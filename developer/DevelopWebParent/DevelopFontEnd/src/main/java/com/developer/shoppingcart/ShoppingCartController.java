package com.developer.shoppingcart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.developer.Utility;
import com.developer.address.AddressService;
import com.developer.common.entity.Address;
import com.developer.common.entity.CartItem;
import com.developer.common.entity.Customer;
import com.developer.common.entity.ShippingRate;
import com.developer.common.exception.CustomerNotFoundException;
import com.developer.customer.CustomerService;
import com.developer.shipping.ShippingRateService;

@Controller
public class ShoppingCartController {

	@Autowired private ShoppingCartService cartService;
	@Autowired private CustomerService customerService;
	@Autowired private AddressService addressService;
	@Autowired private ShippingRateService shipService;
	
	@GetMapping("/cart")
	public String viewCart(Model model, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> cartItems = cartService.listCartItems(customer);
		
		float estimateToltal = 0.0F;
		
		for(CartItem item : cartItems) {
			estimateToltal += item.getSubtotal();
		}
		
		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;
		boolean usePrimaryAddressAsDefault = false;
		
		if(defaultAddress != null) {
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		}else {
			usePrimaryAddressAsDefault = true;
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}
		
		model.addAttribute("pageTitle", "Giỏ hàng");
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("estimateToltal", estimateToltal);
		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
		model.addAttribute("shippingSupported", shippingRate != null);
		
		return "cart/shopping_cart";
	}
	
 	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
 		String email = Utility.getEmailOfAuthenticatedCustomer(request);
 		return customerService.getCustomerByEmail(email);
 	}
}
