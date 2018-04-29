package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_Address;

public interface Employee_AddressRepository {

	Employee_Address findEmployeeAddressById(int id) throws DataAccessException;
	
	Collection<Employee_Address> findEmployeeAddressesByOwnerId(int owner_id) throws DataAccessException;
	
	void save(Employee_Address address) throws DataAccessException;
	
	void deleteEmployeeAddressById(int id) throws DataAccessException;
}
