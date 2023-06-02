package com.developer.shoppingcart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.Utility;
import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;
import com.developer.common.exception.CustomerNotFoundException;
import com.developer.customer.CustomerService;
import com.developer.product.ProductService;

@RestController
public class ShoppingCartRestController {

 	@Autowired private ShoppingCartService cartService;
 	@Autowired private CustomerService customerService;
 	@Autowired private ProductService productService;
 	
 	@PostMapping("/cart/add/{productId}/{quantity}")
 	public String addProductToCart(@PathVariable("productId") Integer productId,
 			@PathVariable("quantity") Integer quantity, HttpServletRequest request,
 			Model model) {
 		
 		try {

 			Customer customer = getAuthenticatedCustomer(request);
			Product productInStock = productService.get(productId);

			if (quantity > productInStock.getQty()) {
				throw new ShoppingCartException("Đã vượt quá sản phẩm hiện có trong kho");
			}
 			
 			Integer updatedQuantity = cartService.addproduct(productId, quantity, customer);
 			return "Đã thêm vào giỏ hàng: " + updatedQuantity ;
 		}catch (CustomerNotFoundException ex) {
			return "Đăng nhập để thêm vào giỏ hàng!";
		}catch (ShoppingCartException ex) {
			return ex.getMessage();
		}
 		
 	}
 	
 	private Customer getAuthenticatedCustomer(HttpServletRequest request) 
 			throws CustomerNotFoundException {
 		String email = Utility.getEmailOfAuthenticatedCustomer(request);
 		if(email == null) {
 			throw new CustomerNotFoundException("Tài khoản chưa xác thực!");
 		}
 		return customerService.getCustomerByEmail(email);
 	}
 	
 	@PostMapping("/cart/update/{productId}/{quantity}")
 	public String updateQuantity(@PathVariable("productId") Integer productId,
 			@PathVariable("quantity") Integer quantity, HttpServletRequest request) throws ShoppingCartException {
 		
 		try {
 			Product productInStock = productService.get(productId);
 			
 			if(quantity > productInStock.getQty()) {
 				throw new ShoppingCartException("Đã vượt quá sản phẩm hiện có trong kho");
 			}
 			
 			Customer customer = getAuthenticatedCustomer(request);
 			float subtotal = cartService.updateQuantity(productId, quantity, customer);
 			
 			return String.valueOf(subtotal);
 		}catch (CustomerNotFoundException ex) {
			return "Đăng nhập để thay đổi số lượng!";
		}
 	}
 	
 	@DeleteMapping("/cart/remove/{productId}")
 	public String removeProduct(@PathVariable("productId") Integer productId,
 			HttpServletRequest request) {
 		try {
 			Customer customer = getAuthenticatedCustomer(request);
 			cartService.removeProduct(productId, customer);
 			return "Đã xóa sản phẩm khỏi giỏ hàng!";
		} catch (CustomerNotFoundException e) {
			return "Đăng nhập để xóa sản phẩm trong giỏ hàng!";
		}
 	}
}
