package com.developer.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.Role;
import com.developer.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserReposiroty repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userNghia = new User("admin@admin.com",  "admin", "admin", "0368023380");
		userNghia.addRole(roleAdmin);
		User saveUser = repo.save(userNghia);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userAnh = new User("anhnnn@gmail.com", "nghia", "le thi lan anhhhh", "0368023380");
		Role roleEditor = new Role(2);
		Role roleAssistant = new Role(4);
		userAnh.addRole(roleEditor);
		userAnh.addRole(roleAssistant);

		User saveUser = repo.save(userAnh);

		assertThat(saveUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUser() {
		Iterable<User> listUser = repo.findAll();
		listUser.forEach(user -> System.out.println(user));

	}

	@Test
	public void testGetUserById() {
		User nghia = repo.findById(1).get();
		System.out.println(nghia);
		assertThat(nghia).isNotNull();
	}

	@Test
	public void testsUpdateUserDetail() {
		User nghia = repo.findById(1).get();
		nghia.setEnabled(true);
		nghia.setEmail("nghiaaaa@gmail.com");
		repo.save(nghia);
	}
	
	@Test
	public void testUpdateUserRole() {
		User anh = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSale = new Role(2);
		
		anh.getRoles().remove(roleEditor);
		anh.addRole(roleSale);
		repo.save(anh);
	}
	
	@Test
	public void deleteUserById() {
		Integer anh = 2;
		repo.deleteById(anh);
	}
	
	@Test
	public void testCreateNewUserWithDegree() {
		User userAnh = new User("nghiaeeeee@gmail.com", "nghiaeeee", "l.anh", "0368023380");
		User saveUser = repo.save(userAnh);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateNewUserWithMarital() {
		User userAnh = new User("nghiarrrrr@gmail.com", "nghiarrr", "l.anh", "0368023380");
		User saveUser = repo.save(userAnh);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "nghia.nguyen1622@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	@Test
	public void testGetUserByphone() {
		String phone = "0368023380";
		User user = repo.getUserByPhone(phone);
		
		assertThat(user).isNotNull();
	}
	@Test
	public void testCountById() {
		Integer id = 19;
		Long countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	@Test
	public void testEnabledUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFisrtPage() {
		int pageNumber = 1;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<User> page = repo.findAll(pageable);
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "l";
		
		int pageNumber = 1;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<User> page = repo.findAll(keyword, pageable);
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isGreaterThan(0);
	}


}
