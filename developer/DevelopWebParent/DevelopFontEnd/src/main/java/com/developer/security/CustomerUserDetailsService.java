package com.developer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.developer.common.entity.Customer;
import com.developer.customer.CustomerRepository;

public class CustomerUserDetailsService implements UserDetailsService{
	
	@Autowired private CustomerRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = repo.findByEmail(email);
		if(customer == null)
			throw new UsernameNotFoundException("Không tìm thấy thông tin người dùng" + email);
		return new CustomerUserDetails(customer);
	}

}
