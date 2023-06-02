package com.developer.order;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.developer.checkout.CheckoutInfo;
import com.developer.common.entity.Address;
import com.developer.common.entity.CartItem;
import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;
import com.developer.common.entity.order.Order;
import com.developer.common.entity.order.OrderDetail;
import com.developer.common.entity.order.OrderStatus;
import com.developer.common.entity.order.OrderTrack;
import com.developer.common.entity.order.PaymentMethod;
import com.developer.configure.BaseURL;
import com.developer.customer.activity.CustomerActivityService;
import com.developer.product.ProductRepository;

@Service
public class OrderService {
	
	@Autowired private OrderRepository repo;
	
	@Autowired private ProductRepository productRepo;
	
	public static final int ORDERS_PER_PAGE = 5;
	
	// 06/04/2023 action by customer.
	@Autowired
	private CustomerActivityService customerActivityService;

	public Order createOrder(Customer customer, Address address, List<CartItem> cartItems,
			PaymentMethod paymentMethod, CheckoutInfo checkoutInfo) {
		Order newOrder = new Order();
		newOrder.setOrderTime(new Date());
		
		
		newOrder.setStatus(OrderStatus.NEW);
		
		newOrder.setCustomer(customer);
		newOrder.setProductCost(checkoutInfo.getProductCost());
		newOrder.setSubtotal(checkoutInfo.getProductTotal());
		newOrder.setShippingCost(checkoutInfo.getShippingCostTotal());
		newOrder.setTax(0.0f);
		newOrder.setTotal(checkoutInfo.getPaymentTotal());
		newOrder.setPaymentMethod(paymentMethod);
		newOrder.setDeliverDays(checkoutInfo.getDeliverDays());
		newOrder.setDeliverDate(checkoutInfo.getDeliverDate());
		
		if (address == null) {
			newOrder.copyAddressFromCustomer();
		} else {
			newOrder.copyShippingAddress(address);
		}
		
		Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
		
		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(newOrder);
			orderDetail.setProduct(product);
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetail.setUnitPrice(product.getDiscountPriceSale());
			orderDetail.setProductCost(product.getCost() * cartItem.getQuantity());
			orderDetail.setSubtotal(cartItem.getSubtotal());
			orderDetail.setShippingCost(cartItem.getShippingCost());
			
			orderDetails.add(orderDetail);
			
			// Deduct the quantity of the product
		    int newQty = product.getQty() - cartItem.getQuantity();
		    if (newQty < 0) {
		        throw new IllegalArgumentException("Không đủ số lượng trong kho: " + product.getId());
		    }
		    product.setQty(newQty);
			
		}
		
		OrderTrack track = new OrderTrack();
		track.setOrder(newOrder);
		track.setStatus(OrderStatus.NEW);
		track.setNotes(OrderStatus.NEW.defaultDescription());
		track.setUpdatedTime(new Date());
		
		newOrder.getOrderTracks().add(track);
		
		
		// Save the updated products to the database
		productRepo.saveAll(cartItems.stream().map(CartItem::getProduct).collect(Collectors.toList()));
		
		return repo.save(newOrder);
	}
	
	public Page<Order> listForCustomerByPage(Customer customer, int pageNum) {
		
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE);
		
		return repo.findAll(customer.getId(), pageable);
	}
	
	public Order getOrder(Integer id, Customer customer) {
		return repo.findByIdAndCustomer(id, customer);
	}
}
