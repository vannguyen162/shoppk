package com.developer.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.Country;
import com.developer.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {
	
	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateCustomer1() {
		Integer countryId = 1;
		Country country = entityManager.find(Country.class, countryId);
		
		Customer customer = new Customer();
		customer.setCountry(country);
		customer.setFirstName("Nghia");
		customer.setLastName("Nguyen Van");
		customer.setPassword("123456");
		customer.setEmail("vannguyen002st@gmail.com");
		customer.setPhoneNumber("0368023380");
		customer.setAddressLine1("241/2A duong D1");
		customer.setCity("Binh Duong");
		customer.setState("Di An");
		customer.setPostalCode("75300");
		customer.setCreatedTime(new Date());
		
		Customer savedCustomer = repo.save(customer);
		
		assertThat(savedCustomer).isNotNull();
		assertThat(savedCustomer.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListCustomers() {
		Iterable<Customer> customer = repo.findAll();
		customer.forEach(System.out::println);
		assertThat(customer).hasSizeGreaterThan(1);
	}
	@Test
	public void testUpdateCustomer() {
		Integer customerId = 1;
		String lastName = "Nguyen Van";
		
		Customer customer = repo.findById(customerId).get();
		customer.setLastName(lastName);
		customer.setEnabled(true);
		Customer updateCustomer = repo.save(customer);
		assertThat(updateCustomer.getLastName()).isEqualTo(lastName);
		
	}
	@Test
	public void findByEmail() {
		String email = "vannguyen002st@gmail.com";
		Customer customer = repo.findByEmail(email);
		
		assertThat(customer).isNotNull();
		System.out.println(customer);
	}
	
	@Test
	public void findByVerificationCode() {
		String code = "";
		Customer customer = repo.findByVerificationCode(code);
		
		assertThat(customer).isNotNull();
		System.out.println(customer);
	}
}
