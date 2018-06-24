package com.mike.xie.maple_tiger_sys.organization.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.mike.xie.maple_tiger_sys.model.NamedFile;
import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.model.Employee;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_File;
import com.mike.xie.maple_tiger_sys.organization.model.Project;
import com.mike.xie.maple_tiger_sys.organization.service.file.UploadFileResponse;
import com.mike.xie.maple_tiger_sys.organization.util.TreeNode;
import com.mike.xie.maple_tiger_sys.security.model.User;

public interface OrganizationService {
	
	void saveUser(User user) throws DataAccessException;
	
	Department findDepartmentById(int id) throws DataAccessException;
	Collection<Department> findAllDepartments() throws DataAccessException;
	TreeNode<Department> findTreeFromDepartments() throws DataAccessException;
	Collection<Department> findDepartmentByName(String name) throws DataAccessException;
	void saveDepartment(Department department) throws DataAccessException;
	boolean saveDepartmentWithNewParent(Department department) throws DataAccessException;
	ResponseEntity<Department> updateDepartment(Department department, int id) throws DataAccessException;
	// public void saveNewDepartmentEmail(Department_Email email) throws DataAccessException;
	void deleteDepartmentEmailById(int id) throws DataAccessException;
	void deleteDepartmentAddressById(int id) throws DataAccessException;
	void deleteDepartmentPhoneById(int id) throws DataAccessException;
	void deleteDepartmentHistoryById(int id) throws DataAccessException;	
	void deleteDepartmentById(int id) throws DataAccessException;
	
	Employee findEmployeeById(int id) throws DataAccessException;
	Collection<Employee> findEmployeeByDepartmentId(int deptId) throws DataAccessException;
	
	Employee_File findEmployeeFileById(int id) throws DataAccessException;
	
	void saveEmployeeWithNewDepartment(Employee employee) throws DataAccessException;
	void saveEmployee(Employee employee) throws DataAccessException;
	ResponseEntity<Employee> updateEmployee(Employee employee, int id) throws DataAccessException;
	ResponseEntity<Employee> updateEmployeeAssignments(Employee employee, int id) throws DataAccessException;
	ResponseEntity<NamedFile> uploadFile(int employeeId, MultipartFile file) throws DataAccessException;
	
	void deleteEmployeeById(int id) throws DataAccessException;
	void deleteEmployeeEmailById(int id) throws DataAccessException;
	void deleteEmployeeAddressById(int id) throws DataAccessException;
	void deleteEmployeePhoneById(int id) throws DataAccessException;
	void deleteEmployeeHistoryById(int id) throws DataAccessException;
	void deleteEmployeeAssignmentById(int id) throws DataAccessException;
	void deleteEmployeeFileById(int id) throws DataAccessException;
	ResponseEntity<Void> deleteEmployeeFile(int fileId) throws DataAccessException;
	
	Collection<Project> findAllProjects() throws DataAccessException;
}
