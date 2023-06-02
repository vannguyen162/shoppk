package com.developer.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.developer.common.entity.Brand;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BrandServiceTests {
	@MockBean
	private BrandRepository repo;
	
	@InjectMocks
	private BrandService service;

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer id = null;
		String name = "Lenovo";
		Brand brand = new Brand(name);

		Mockito.when(repo.findByName(name)).thenReturn(brand);

		String result = service.checkUnique(id, name);
		
		assertThat(result).isEqualTo("Duplicate");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnOk() {
		Integer id = null;
		String name = "Lenovo";
		
		Mockito.when(repo.findByName(name)).thenReturn(null);
		String result = service.checkUnique(id, name);
		
		assertThat(result).isEqualTo("OK");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicate() {
		Integer id = 1;
		String name = "Lenovo";
		Brand brand = new Brand(id, name);
		
		Mockito.when(repo.findByName(name)).thenReturn(brand);
		
		String result = service.checkUnique(2, "Lenovo");
		
		assertThat(result).isEqualTo("Duplicate");
	}
}
