package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Assignment;

public interface Employee_AssignmentRepository {

	Assignment findEmployeeAssignmentById(int id) throws DataAccessException;
	
	Collection<Assignment> finalEmployeeAssignmentsByEmployeeId(int employee_id) throws DataAccessException;
	
	void save(Assignment assignment) throws DataAccessException;
	
	void deleteEmployeeAssignmentById(int id) throws DataAccessException;
}
