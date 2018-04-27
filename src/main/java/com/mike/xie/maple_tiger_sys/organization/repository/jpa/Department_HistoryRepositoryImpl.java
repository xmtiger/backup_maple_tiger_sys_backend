package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Department_History;
import com.mike.xie.maple_tiger_sys.organization.repository.Department_HistoryRepository;

@Repository
@Profile("jpa")
public class Department_HistoryRepositoryImpl implements Department_HistoryRepository {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Department_History findDepartmentHistoryById(int id) throws DataAccessException {
		return em.find(Department_History.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Department_History> findDepartmentHistoriesByOwnerId(int owner_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Department_History v where v.owner.id= :id");
        query.setParameter("id", owner_id);
        return query.getResultList();
	}

	@Override
	public void save(Department_History history) throws DataAccessException {
		if(history.getId() == null){
            this.em.persist(history);
        } else{
            this.em.merge(history);
        }
		
	}

	@Override
	public void deleteDepartmentHistoryById(int id) throws DataAccessException {
		if(id > 0){
            Department_History history = this.findDepartmentHistoryById(id);
            
            this.em.remove(history);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

}
