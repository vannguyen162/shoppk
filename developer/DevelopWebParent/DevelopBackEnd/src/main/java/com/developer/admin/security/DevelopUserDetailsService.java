package com.developer.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.developer.admin.user.UserReposiroty;
import com.developer.common.entity.User;

public class DevelopUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserReposiroty userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if(user != null) {
			return new DevelopUserDetails(user);
		}
		throw new UsernameNotFoundException("Không tìm thấy người dùng với Email: " + email);
	}

}
