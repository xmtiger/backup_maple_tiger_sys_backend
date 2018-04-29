package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Employee;

public interface EmployeeRepository {

	Collection<Employee> findEmployeeByLastName(String lastName) throws DataAccessException;
    
	Collection<Employee> findEmployeesByDepartmentId(int deptId) throws DataAccessException;
	
    Employee findEmployeeById(int id) throws DataAccessException;
    
    void save(Employee employee) throws DataAccessException;
    
    void deleteEmployeeById(int id) throws DataAccessException;
}
