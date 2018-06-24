package com.mike.xie.maple_tiger_sys.security.repository;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.security.model.Role;

public interface RoleRepository {

	Role findRoleById(int id) throws DataAccessException;
	
	Role findRoleByName(String name) throws DataAccessException;
	
	void save(Role role) throws DataAccessException;
	
	void deleteRoleById(int id) throws DataAccessException;
}
