package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Assignment;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_AssignmentRepository;

@Repository
@Profile("jpa")
public class Employee_AssignmentRepositoryImpl implements Employee_AssignmentRepository{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Assignment findEmployeeAssignmentById(int id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT DISTINCT assignment FROM Assignment assignment WHERE assignment.id = :id");
        query.setParameter("id", id);
        return (Assignment) query.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Assignment> finalEmployeeAssignmentsByEmployeeId(int employee_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Assignment v where v.employee.id= :id");
        query.setParameter("id", employee_id);
        return query.getResultList();
	}

	@Override
	public void save(Assignment assignment) throws DataAccessException {
		if(assignment.getId() != null && assignment.getId() <= 0) {
			assignment.setId(null);
		}
		
		if(assignment.getId() == null){
            this.em.persist(assignment);
        } else{
            this.em.merge(assignment);
        }
		
	}

	@Override
	public void deleteEmployeeAssignmentById(int id) throws DataAccessException {
		if(id > 0){
            Assignment assignment = this.findEmployeeAssignmentById(id);
            
            this.em.remove(assignment);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

}
