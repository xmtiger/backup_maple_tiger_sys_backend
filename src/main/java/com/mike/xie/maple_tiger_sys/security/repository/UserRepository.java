package com.mike.xie.maple_tiger_sys.security.repository;

import java.util.Optional;

import com.mike.xie.maple_tiger_sys.security.model.User;


public interface UserRepository {
	
	Optional<User> findByName(String username);
	
}
