package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Employee;

public interface EmployeeRepository {

	Collection<Employee> findByLastName(String lastName) throws DataAccessException;
    
    Employee findById(int id) throws DataAccessException;
    
    void save(Employee employee) throws DataAccessException;
}
