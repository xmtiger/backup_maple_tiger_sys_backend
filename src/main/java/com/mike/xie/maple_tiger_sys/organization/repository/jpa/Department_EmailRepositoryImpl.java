package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Department_Email;
import com.mike.xie.maple_tiger_sys.organization.repository.Department_EmailRepository;

@Repository
@Profile("jpa")
public class Department_EmailRepositoryImpl implements Department_EmailRepository {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Department_Email findDepartmentEmailById(int id) throws DataAccessException {
		return em.find(Department_Email.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Department_Email> finalDepartmentEmailsByOwnerId(int owner_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Department_Email v where v.owner.id= :id");
        query.setParameter("id", owner_id);
        return query.getResultList();
	}

	@Override
	public void save(Department_Email departmentEmail) throws DataAccessException {
		if(departmentEmail.getId() != null && departmentEmail.getId() <= 0) {
			departmentEmail.setId(null);
		}
		
		if(departmentEmail.getId() == null ){
            this.em.persist(departmentEmail);
        } else{
            this.em.merge(departmentEmail);
        }
		
	}

	@Override
	public void deleteDepartmentEmailById(int id) throws DataAccessException {
		if(id > 0){
            Department_Email email = this.findDepartmentEmailById(id);
            
            this.em.remove(email);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

}
