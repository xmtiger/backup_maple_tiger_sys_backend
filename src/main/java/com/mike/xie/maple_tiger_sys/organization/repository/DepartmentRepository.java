package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Department;

public interface DepartmentRepository {
	 
	Department findDepartmentById(int id) throws DataAccessException;
	    
	Collection<Department> findDepartmentByName(String name) throws DataAccessException;
	    
	void save(Department department) throws DataAccessException;
	    
	Collection<Department> findAllDepartments() throws DataAccessException;
		    
	void deleteDepartmentById(int deptId) throws DataAccessException;
}
