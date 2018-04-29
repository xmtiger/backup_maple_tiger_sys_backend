package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_History;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_HistoryRepository;

@Repository
@Profile("jpa")
public class Employee_HistoryRepositoryImpl implements Employee_HistoryRepository {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Employee_History findEmployeeHistoryById(int id) throws DataAccessException {
		return em.find(Employee_History.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Employee_History> findEmployeeHistoriesByOwnerId(int owner_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Employee_History v where v.owner.id= :id");
        query.setParameter("id", owner_id);
        return query.getResultList();
	}

	@Override
	public void save(Employee_History history) throws DataAccessException {
		if(history.getId() == null){
            this.em.persist(history);
        } else{
            this.em.merge(history);
        }
		
	}

	@Override
	public void deleteEmployeeHistoryById(int id) throws DataAccessException {
		if(id > 0){
            Employee_History history = this.findEmployeeHistoryById(id);
            
            this.em.remove(history);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

}
