package com.developer.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.Brand;
import com.developer.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {
	
	@Autowired
	private BrandRepository repo;
	
	@Test
	public void testCreateBrand1() {
		Category laptops = new Category(6);
		Brand acer = new Brand("Acer");
		acer.getCategories().add(laptops);
		
		Brand savedBrand = repo.save(acer);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThanOrEqualTo(0);
		
	}
	@Test
	public void testCreateBrand2() {
		Category cellphones = new Category(2);
		Category tablets = new Category(4);
		
		Brand apple = new Brand("Apple");
		apple.getCategories().add(cellphones);
		apple.getCategories().add(tablets);
		
		Brand savedBrand = repo.save(apple);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThanOrEqualTo(0);
		
	}
	@Test
	public void testFindAll() {
		Iterable<Brand> brands = repo.findAll();
		brands.forEach(System.out :: println);	
		assertThat(brands).isNotEmpty();
		
	}

}
