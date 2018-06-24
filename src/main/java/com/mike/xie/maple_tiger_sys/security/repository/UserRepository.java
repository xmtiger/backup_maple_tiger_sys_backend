package com.mike.xie.maple_tiger_sys.security.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.security.model.User;


public interface UserRepository {
		
	Optional<User> findByName(String username);
	
	Collection<User> findUsersByName(String username);
	
	User findUserById(int id) throws DataAccessException;
	
	void save(User user) throws DataAccessException;
	
	void deleteUserById(int id) throws DataAccessException;
}
