package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_History;

public interface Employee_HistoryRepository {
	Employee_History findEmployeeHistoryById(int id) throws DataAccessException;
	
	Collection<Employee_History> findEmployeeHistoriesByOwnerId(int owner_id) throws DataAccessException;
	
	void save(Employee_History history) throws DataAccessException;
	
	void deleteEmployeeHistoryById(int id) throws DataAccessException;
}
