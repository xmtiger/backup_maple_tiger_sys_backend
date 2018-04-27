package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Department_Phone;

public interface Department_PhoneRepository {

	Department_Phone findDepartmentPhoneById(int id) throws DataAccessException;
	
	Collection<Department_Phone> findDepartmentPhonesByOwnerId(int owner_id) throws DataAccessException;
	
	void save(Department_Phone phone) throws DataAccessException;
	
	void deleteDepartmentPhoneById(int id) throws DataAccessException;
}
