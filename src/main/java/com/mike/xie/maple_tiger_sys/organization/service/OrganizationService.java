package com.mike.xie.maple_tiger_sys.organization.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Department;

public interface OrganizationService {
	
	Department findDepartmentById(int id) throws DataAccessException;
	public Collection<Department> findDepartmentByName(String name) throws DataAccessException;
	void saveDepartment(Department department) throws DataAccessException;
	void deleteDepartmentById(int id) throws DataAccessException;
}
