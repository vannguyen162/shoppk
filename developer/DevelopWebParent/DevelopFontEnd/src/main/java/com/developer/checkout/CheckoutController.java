package com.developer.checkout;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.developer.Utility;
import com.developer.address.AddressService;
import com.developer.common.entity.Address;
import com.developer.common.entity.CartItem;
import com.developer.common.entity.Customer;
import com.developer.common.entity.ShippingRate;
import com.developer.common.entity.order.Order;
import com.developer.common.entity.order.PaymentMethod;
import com.developer.configure.BaseURL;
import com.developer.customer.CustomerService;
import com.developer.customer.activity.CustomerActivityService;
import com.developer.order.OrderService;
import com.developer.setting.CurrencySettingBag;
import com.developer.setting.EmailSettingBag;
import com.developer.setting.SettingService;
import com.developer.shipping.ShippingRateService;
import com.developer.shoppingcart.ShoppingCartService;

@Controller
public class CheckoutController {

	@Autowired
	private CheckoutService checkoutService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ShippingRateService shipService;
	@Autowired
	private ShoppingCartService cartService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private SettingService settingService;
	
	// 06/04/2023 action by customer.
	@Autowired
	private CustomerActivityService customerActivityService;

	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);

		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;

		if (defaultAddress != null) {
			model.addAttribute("shippingAdress", defaultAddress.toString());
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		} else {
			model.addAttribute("shippingAdress", customer.toString());
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}

		if (shippingRate == null) {
			return "redirect:/cart";
		}

		List<CartItem> cartItems = cartService.listCartItems(customer);
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);
		
		model.addAttribute("checkoutInfo", checkoutInfo);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("pageTitle", "Thanh toán");

		return "checkout/checkout";
	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		return customerService.getCustomerByEmail(email);
	}
	
	@PostMapping("/place_order")
	public String placeOrder(HttpServletRequest request) 
			throws UnsupportedEncodingException, MessagingException {
		String paymentType = request.getParameter("paymentMethod");
		PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentType);
		
		Customer customer = getAuthenticatedCustomer(request);

		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;

		if (defaultAddress != null) {
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		} else {
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}

		List<CartItem> cartItems = cartService.listCartItems(customer);
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);
		
		// 06/04/2023 action by customer.
		BaseURL.setUrl("order/detail/");
		
		Order createdOrder = orderService.createOrder(customer, defaultAddress, cartItems, paymentMethod, checkoutInfo);
		cartService.deleteByCustomer(customer);
		
		sendOrderConfirmationEmail(request, createdOrder);
		
		// 06/04/2023 action by customer.
		String activity = "Đã đặt hàng";
		String url = BaseURL.getUrl() + createdOrder.getId();
		customerActivityService.save(activity, customer, url);
		
		return "checkout/order_completed";
	}

	private void sendOrderConfirmationEmail(HttpServletRequest request, Order order) 
			throws UnsupportedEncodingException, MessagingException {
		EmailSettingBag emailSettings = settingService.getEmailSettings();
		JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
		mailSender.setDefaultEncoding("utf-8");
		Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.smtp.connectiontimeout", "10000");
		
		String toAddress = order.getCustomer().getEmail();
		String subject = emailSettings.getOrderConfirmationSubject();
		String content = emailSettings.getOrderConfirmationContent();
		
		subject = subject.replaceFirst("[[orderId]]", String.valueOf(order.getId()));
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);
		
		DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss E, dd MMM yyyy");
		String orderTime = dateFormatter.format(order.getOrderTime());
		
		CurrencySettingBag currencySettings = settingService.getCurrencySettings();
		String totalAmount = Utility.formatCurrency(order.getTotal(), currencySettings);
		
		content = content.replace("[[name]]", order.getCustomer().getFullName());
		content = content.replace("[[orderId]]", String.valueOf(order.getId()));
		content = content.replace("[[oderTime]]", orderTime);
		content = content.replace("[[shippingAddress]]", order.getShippingAddress());
		content = content.replace("[[total]]", totalAmount);
		content = content.replace("[[paymentMethod]]", order.getPaymentMethod().toString());
		
		helper.setText(content, true);
		
		mailSender.send(message);
		
	}

}
