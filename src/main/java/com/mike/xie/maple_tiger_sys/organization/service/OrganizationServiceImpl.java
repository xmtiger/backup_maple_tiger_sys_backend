package com.mike.xie.maple_tiger_sys.organization.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.repository.DepartmentRepository;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Department findDepartmentById(int id) throws DataAccessException {
		return departmentRepository.findDepartmentById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Department> findDepartmentByName(String name) throws DataAccessException {
		return departmentRepository.findDepartmentByName(name);
	}	

	@Override
	@Transactional
	public void saveDepartment(Department department) throws DataAccessException {
		
		departmentRepository.save(department);
	}

	@Override
	@Transactional
	public void deleteDepartmentById(int id) throws DataAccessException {
		
		departmentRepository.deleteDepartmentById(id);
	}

}
