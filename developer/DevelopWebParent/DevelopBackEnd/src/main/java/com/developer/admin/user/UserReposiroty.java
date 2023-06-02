package com.developer.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.developer.admin.paging.SearchRepository;
import com.developer.common.entity.User;

public interface UserReposiroty extends SearchRepository<User, Integer>{
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u WHERE u.phone = :phone")
	public User getUserByPhone(@Param("phone") String phone);
	
	public Long countById(Integer id);
	
	@Query("SELECT u FROM User u WHERE CONCAT(u.fullName, ' ', u.email, ' ', u.phone, ' ',"
			+ " u.identity_card) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

}
