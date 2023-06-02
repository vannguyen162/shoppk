package com.developer.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.Brand;
import com.developer.common.entity.Category;
import com.developer.common.entity.Product;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct() {
		Brand brand = entityManager.find(Brand.class, 6);
		Category category = entityManager.find(Category.class, 6);
		
		Product product = new Product();
		product.setName("Assus");
		product.setAlias("Assus 15");
		product.setShortDescription("this is Assus short Decription");
		product.setFullDescription("full Decription");
		
		product.setBrand(brand);
		product.setCategory(category);
		
		product.setPrice(456);
		product.setCost(400);
		product.setEnabled(true);
		product.setInStock(true);
		
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		
		Product savedProduct = repo.save(product);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThanOrEqualTo(0);
	}
	
	@Test
	public void testListAllProducts() {
		Iterable<Product> iterableProducts = repo.findAll();
		
		iterableProducts.forEach(System.out :: println);
	}
	
	@Test
	public void testGetProducts() {
		Integer id = 2;
		Product product = repo.findById(id).get();
		System.out.println(product);
		assertThat(product).isNotNull();
	}
	
	@Test
	public void testUpdateProducts() {
		Integer id = 1;
		Product product = repo.findById(id).get();
		product.setPrice(789);
		repo.save(product);
		
		Product updateProduct = entityManager.find(Product.class, id);

		assertThat(updateProduct.getPrice()).isEqualTo(789);
	}
	@Test
	public void testDeleteProducts() {
		Integer id = 3;
		repo.deleteById(id);
		
		Optional<Product> result = repo.findById(id);
		
		assertThat(!result.isPresent());
	}
	@Test
	public void testSaveProductWithImages() {
		Integer productId = 1;
		Product product =  repo.findById(productId).get();
		
		product.setMainImage("main image.jpg");
		product.addExtraImage("extra image 1.png");
		product.addExtraImage("extra_image_2.png");
		product.addExtraImage("extra-image3.png");
		
		Product saveProduct = repo.save(product);
		
		assertThat(saveProduct.getImages().size()).isEqualTo(3);
	}
	@Test
	public void testSaveProductWithDetails() {
		Integer productId = 1;
		Product product =  repo.findById(productId).get();
		
		product.addDetail("Devery", "512GB");
		product.addDetail("CPU", "Mediatek");
		product.addDetail("OS", "Android");
		
		Product saveProduct = repo.save(product);
		assertThat(saveProduct.getDetails()).isNotEmpty();
	}
}
