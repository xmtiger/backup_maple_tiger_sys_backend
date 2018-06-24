package com.mike.xie.maple_tiger_sys.organization.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.mike.xie.maple_tiger_sys.organization.model.Project;

public interface ProjectRepository {
	
	Collection<Project> findAllProjects() throws DataAccessException;

}
