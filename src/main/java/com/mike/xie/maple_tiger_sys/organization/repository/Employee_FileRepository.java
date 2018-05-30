package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_File;

public interface Employee_FileRepository {

	Employee_File findEmployeeFileById(int id) throws DataAccessException;
	
	Collection<Employee_File> finalEmployeeFilesByOwnerId(int owner_id) throws DataAccessException;
	
	void save(Employee_File employeeFile) throws DataAccessException;
	
	void deleteEmployeeFileById(int id) throws DataAccessException;
}
