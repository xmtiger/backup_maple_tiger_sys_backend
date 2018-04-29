package com.mike.xie.maple_tiger_sys.organization.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.model.Employee;
import com.mike.xie.maple_tiger_sys.organization.util.TreeNode;

public interface OrganizationService {
	
	Department findDepartmentById(int id) throws DataAccessException;
	Collection<Department> findAllDepartments() throws DataAccessException;
	TreeNode<Department> findTreeFromDepartments() throws DataAccessException;
	Collection<Department> findDepartmentByName(String name) throws DataAccessException;
	void saveDepartment(Department department) throws DataAccessException;
	void deleteDepartmentById(int id) throws DataAccessException;
	
	Employee findEmployeeById(int id) throws DataAccessException;
	public Collection<Employee> findEmployeeByDepartmentId(int deptId) throws DataAccessException;
	void saveEmployee(Employee employee) throws DataAccessException;
	void deleteEmployeeById(int id) throws DataAccessException;
}
