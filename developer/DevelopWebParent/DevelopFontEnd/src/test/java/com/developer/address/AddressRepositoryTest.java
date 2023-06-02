package com.developer.address;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.Address;
import com.developer.common.entity.Category;
import com.developer.common.entity.Country;
import com.developer.common.entity.Customer;



@DataJpaTest()
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AddressRepositoryTest {

	@Autowired
	private AddressRepository repo;
	
	@Test
	public void testAddNew() {
		Integer cusId = 10;
		Integer counId = 1;
		Address address = new Address();
		address.setCustomer(new Customer(cusId));
		address.setCountry(new Country(counId));
		address.setFirstName("nguyen van");
		address.setLastName("hieu");
		address.setPhoneNumber("0368023380");
		address.setAddressLine1("93");
		address.setAddressLine2("duong d1");
		address.setCity("Di an");
		address.setState("Di an");
		address.setPostalCode("71500");
		Address saveAddress = repo.save(address);
		assertThat(saveAddress.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testUpdate() {
		Integer id = 1;
		Address address = repo.findById(id).get();
		
		address.setDefaultForShipping(true);
		Address saveAddress = repo.save(address);
	}
	
}

