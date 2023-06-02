package com.developer.shoppingcart;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.CartItem;
import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;


@DataJpaTest()
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartItemRepositoryTests {
	
	@Autowired private CartItemRepository repo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testSaveItem() {
		Integer cus = 1;
		Integer pdt = 32;
		
		Customer customer = entityManager.find(Customer.class, cus);
		Product product = entityManager.find(Product.class, pdt);
		
		CartItem cartItem = new CartItem();
		cartItem.setCustomer(customer);
		cartItem.setProduct(product);
		cartItem.setQuantity(2);
		
		CartItem saveIt = repo.save(cartItem);
		
		assertThat(saveIt.getId()).isGreaterThan(0);
	}

}
