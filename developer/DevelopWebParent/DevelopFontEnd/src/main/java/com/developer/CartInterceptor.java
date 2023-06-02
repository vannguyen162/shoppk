package com.developer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.developer.common.entity.CartItem;
import com.developer.common.entity.Customer;
import com.developer.customer.CustomerService;
import com.developer.shoppingcart.ShoppingCartService;

public class CartInterceptor extends HandlerInterceptorAdapter{
	
    private final ShoppingCartService cartService;
    private final ApplicationContext applicationContext;

    public CartInterceptor(ShoppingCartService cartService, ApplicationContext applicationContext) {
        this.cartService = cartService;
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Customer customer = getAuthenticatedCustomer(request);
        List<CartItem> cartItemsLoged = cartService.listCartItems(customer);
		float estimateToltalLoged = 0.0F;
		
		for(CartItem item : cartItemsLoged) {
			estimateToltalLoged += item.getSubtotal();
		}
        request.setAttribute("cartItemsLoged", cartItemsLoged);
        request.setAttribute("estimateToltalLoged", estimateToltalLoged);
        return true;
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        return customerService.getCustomerByEmail(email);
    }
}