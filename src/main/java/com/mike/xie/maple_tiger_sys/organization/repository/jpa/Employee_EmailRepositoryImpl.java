package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_Email;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_EmailRepository;

@Repository
@Profile("jpa")
public class Employee_EmailRepositoryImpl implements Employee_EmailRepository{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Employee_Email findEmployeeEmailById(int id) throws DataAccessException {
		return em.find(Employee_Email.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Employee_Email> finalEmployeeEmailsByOwnerId(int owner_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Employee_Email v where v.owner.id= :id");
        query.setParameter("id", owner_id);
        return query.getResultList();
	}

	@Override
	public void save(Employee_Email employeeEmail) throws DataAccessException {
		if(employeeEmail.getId() == null){
            this.em.persist(employeeEmail);
        } else{
            this.em.merge(employeeEmail);
        }
		
	}

	@Override
	public void deleteEmployeeEmailById(int id) throws DataAccessException {
		if(id > 0){
            Employee_Email email = this.findEmployeeEmailById(id);
            
            this.em.remove(email);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

}
