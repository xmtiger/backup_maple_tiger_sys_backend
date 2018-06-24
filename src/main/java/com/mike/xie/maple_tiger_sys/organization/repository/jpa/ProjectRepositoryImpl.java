package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Project;
import com.mike.xie.maple_tiger_sys.organization.repository.ProjectRepository;

@Repository
@Profile("jpa")
public class ProjectRepositoryImpl implements ProjectRepository{
	
	@PersistenceContext
    private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Project> findAllProjects() throws DataAccessException {
		Query query = this.em.createQuery("SELECT project FROM Project project");
        return query.getResultList();
	}

}
