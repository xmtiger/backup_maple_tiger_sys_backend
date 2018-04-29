package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_Phone;

public interface Employee_PhoneRepository {

	Employee_Phone findEmployeePhoneById(int id) throws DataAccessException;
	
	Collection<Employee_Phone> findEmployeePhonesByOwnerId(int owner_id) throws DataAccessException;
	
	void save(Employee_Phone phone) throws DataAccessException;
	
	void deleteEmployeePhoneById(int id) throws DataAccessException;
}
