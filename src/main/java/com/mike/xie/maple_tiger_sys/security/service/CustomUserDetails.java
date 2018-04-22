package com.mike.xie.maple_tiger_sys.security.service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mike.xie.maple_tiger_sys.security.model.User;

public class CustomUserDetails implements UserDetails {

	/**
	 * 
	 */
	private final int id;
	private final String username;
	private final String firstname;
	private final String lastName;
	private final String password;
	private final String email;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean enabled;
	private final Date lastPasswordResetDate;
	
	public CustomUserDetails(
			int id, 
			String username, 
			String firstname, 
			String lastname,
			String password, 
			String email, 
			Collection<? extends GrantedAuthority> authorities,
			int enabled, 
			Date lastPasswordResetDate) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastName = lastname;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
		
		if(enabled == 0)
			this.enabled = false;
		else 
			this.enabled = true;
			
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}	
		
	@Override
	public boolean isEnabled() {
		
		return enabled;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

}
