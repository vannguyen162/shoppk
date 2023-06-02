package com.developer.admin.service.settings;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	

	private Collection<? extends GrantedAuthority> authorities;
	
	private String password;

	private String username;

	private Boolean enabled;

	private Boolean accountNonExpired;

	private Boolean accountNonLocked;

	private boolean credentialsNonExpired;
	
	public CustomUserDetails(String username, String password, Boolean enabled, Collection<? extends GrantedAuthority> authorities) {
	    this.enabled=enabled;
	    this.username=username;
	    this.password=password;
	    this.accountNonExpired=true;
	    this.accountNonLocked=true;
	    this.credentialsNonExpired=true;
	    this.authorities=authorities;
	}
	
	public CustomUserDetails(Collection<? extends GrantedAuthority> authorities, String password, String username,
			Boolean enabled, Boolean accountNonExpired, Boolean accountNonLocked, boolean credentialsNonExpired) {
		this.authorities = authorities;
		this.password = password;
		this.username = username;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void eraseCredentials(){
        this.password=null;
    }



}
