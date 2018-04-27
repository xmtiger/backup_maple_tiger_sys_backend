package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Department_Address;

public interface Department_AddressRepository {

	Department_Address findDepartmentAddressById(int id) throws DataAccessException;
	
	Collection<Department_Address> findDepartmentAddressesByOwnerId(int owner_id) throws DataAccessException;
	
	void save(Department_Address address) throws DataAccessException;
	
	void deleteDepartmentAddressById(int id) throws DataAccessException;
}
