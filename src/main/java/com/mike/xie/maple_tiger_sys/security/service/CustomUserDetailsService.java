package com.mike.xie.maple_tiger_sys.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mike.xie.maple_tiger_sys.security.repository.UserRepository;

import com.mike.xie.maple_tiger_sys.security.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> optionalUser = userRepository.findByName(username);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("username not found"));
		
		return CustomUserFactory.create(optionalUser.get());
	}

}
