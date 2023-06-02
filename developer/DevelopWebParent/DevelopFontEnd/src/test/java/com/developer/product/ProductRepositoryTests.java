package com.developer.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.developer.common.entity.Category;
import com.developer.common.entity.Product;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTests {
	
	@Autowired private ProductRepository repo;
	
	@Test
	public void testFindByAlias() {
		String alias = "iphone-xr";
		Product product = repo.findByAlias(alias);
		
		assertThat(product).isNotNull();
	}

}
