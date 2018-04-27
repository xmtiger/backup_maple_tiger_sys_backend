package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Department_Email;

public interface Department_EmailRepository {

	Department_Email findDepartmentEmailById(int id) throws DataAccessException;
	
	Collection<Department_Email> finalDepartmentEmailsByOwnerId(int owner_id) throws DataAccessException;
	
	void save(Department_Email departmentEmail) throws DataAccessException;
	
	void deleteDepartmentEmailById(int id) throws DataAccessException;
}
