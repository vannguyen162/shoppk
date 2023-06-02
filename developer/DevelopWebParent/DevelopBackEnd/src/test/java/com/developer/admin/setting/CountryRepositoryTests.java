package com.developer.admin.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.developer.admin.setting.country.CountryRepository;
import com.developer.common.entity.Country;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CountryRepositoryTests {

	@Autowired private CountryRepository repo;
	
	@Test
	public void testCreateCountry() {
		Country country = repo.save(new Country("Việt Nam" , "VN"));
		assertThat(country).isNotNull();
		assertThat(country.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListCounttries() {
		List<Country> listCountries = repo.findAllByOrderByNameAsc();
		listCountries.forEach(System.out::println);
		
		assertThat(listCountries.size()).isGreaterThan(0);
	}
	
	@Test
	public void testUpdateCountry() {
		Integer id = 1;
		String name = "Korea";
		
		Country country = repo.findById(id).get();
		country.setName(name);
		
		Country updateCountry = repo.save(country);
		
		assertThat(updateCountry.getName()).isEqualTo(name);
	}
	
	@Test
	public void testGetCountry() {
		Integer id = 3;
		Country country = repo.findById(id).get();
		assertThat(country).isNotNull();
	}
	@Test
	public void testDeleteCountry() {
		Integer id = 5;
		repo.deleteById(id);
		
		Optional<Country> findById = repo.findById(id);
		assertThat(findById.isEmpty());
	}
}











