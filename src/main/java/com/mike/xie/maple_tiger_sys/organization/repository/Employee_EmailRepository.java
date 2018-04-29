package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_Email;

public interface Employee_EmailRepository {

	Employee_Email findEmployeeEmailById(int id) throws DataAccessException;
	
	Collection<Employee_Email> finalEmployeeEmailsByOwnerId(int owner_id) throws DataAccessException;
	
	void save(Employee_Email departmentEmail) throws DataAccessException;
	
	void deleteEmployeeEmailById(int id) throws DataAccessException;
}
