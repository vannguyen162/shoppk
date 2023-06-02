package com.developer.shoppingcart;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.common.entity.CartItem;
import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;
import com.developer.configure.BaseURL;
import com.developer.customer.activity.CustomerActivityService;
import com.developer.product.ProductRepository;

@Service
@Transactional
public class ShoppingCartService {

	@Autowired private CartItemRepository cartRepo;
	@Autowired private ProductRepository productRepo;
	
	// 03/04/2023 action by customer.
	@Autowired private CustomerActivityService customerActivityService;
	
	public Integer addproduct(Integer productId, Integer quantity, Customer customer) throws ShoppingCartException {
		Integer updateQuantity = quantity;
		
		Product product = new Product(productId);
		CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);
		
		if(cartItem != null) {
			updateQuantity = cartItem.getQuantity() + quantity;
			
			if(updateQuantity > 5) {
				throw new ShoppingCartException("Tối đa 5 số lượng" + "(Số lượng hiện tại trong giỏ: " + cartItem.getQuantity() +")");
			}
		}else {
			cartItem = new CartItem();
			cartItem.setCustomer(customer);
			cartItem.setProduct(product);
		}
		
		cartItem.setQuantity(updateQuantity);
		
		cartRepo.save(cartItem);
		
		// 06/04/2023 action by customer.
		Product pdt = productRepo.findById(productId).get();
		BaseURL.setUrl("p/" + pdt.getAlias());
		String url = BaseURL.getUrl();
		String activity = "Thêm sản phẩm vào giỏ";
		customerActivityService.save(activity, customer, url);
		
		return updateQuantity;
	}

	public List<CartItem> listCartItems(Customer customer){
		return cartRepo.findByCustomer(customer);
	}
	
	public float updateQuantity(Integer productId, Integer quantity, Customer customer) {
		cartRepo.updateQuantity(quantity, customer.getId(), productId);
		Product product = productRepo.findById(productId).get();
		float subtotal = product.getDiscountPriceSale() * quantity;
		
		// 06/04/2023 action by customer.
		Product pdt = productRepo.findById(productId).get();
		BaseURL.setUrl("p/" + pdt.getAlias());
		String url = BaseURL.getUrl();
		String activity = "Thay đổi số lượng sản phẩm trong giỏ";
		customerActivityService.save(activity, customer, url);
		
		return subtotal;
	}
	
	public void removeProduct(Integer productId, Customer customer) {
		cartRepo.deleteByCustomerAndProduct(customer.getId(), productId);
		
		// 06/04/2023 action by customer.
		Product pdt = productRepo.findById(productId).get();
		BaseURL.setUrl("p/" + pdt.getAlias());
		String url = BaseURL.getUrl();
		String activity = "Đã xóa sản phẩm trong giỏ";
		customerActivityService.save(activity, customer, url);
	}
	
	public void deleteByCustomer(Customer customer) {
		cartRepo.deleteByCustomer(customer.getId());
		
		// 03/04/2023 action by customer.
		String activity = "Đã xóa sản phẩm trong giỏ";
		customerActivityService.save(activity, customer, "");
	}
}
