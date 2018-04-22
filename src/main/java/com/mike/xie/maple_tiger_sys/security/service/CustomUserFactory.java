package com.mike.xie.maple_tiger_sys.security.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.mike.xie.maple_tiger_sys.security.model.Role;
import com.mike.xie.maple_tiger_sys.security.model.User;

public class CustomUserFactory {
	
	public static CustomUserDetails create(User user) {
				
		return new CustomUserDetails(
				user.getId(),
				user.getUsername(),
				user.getFirst_name(),
				user.getLast_name(),
				user.getPassword(),
				user.getEmail(),
				mapToGrantedAuthorities(user.getRoles()),
				user.getEnabled(),
				user.getLast_password_reset_date()
				);
	}
	
	private static Set<GrantedAuthority> mapToGrantedAuthorities(Set<Role> authorities) {
		return authorities.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toSet());
				
	}
	
}
