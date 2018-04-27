package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Department_History;

public interface Department_HistoryRepository {

	Department_History findDepartmentHistoryById(int id) throws DataAccessException;
	
	Collection<Department_History> findDepartmentHistoriesByOwnerId(int owner_id) throws DataAccessException;
	
	void save(Department_History history) throws DataAccessException;
	
	void deleteDepartmentHistoryById(int id) throws DataAccessException;
}
