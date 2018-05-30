package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Department_Phone;
import com.mike.xie.maple_tiger_sys.organization.repository.Department_PhoneRepository;

@Repository
@Profile("jpa")
public class Department_PhoneRepositoryImpl implements Department_PhoneRepository {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Department_Phone findDepartmentPhoneById(int id) throws DataAccessException {
		return em.find(Department_Phone.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Department_Phone> findDepartmentPhonesByOwnerId(int owner_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Department_Phone v where v.owner.id= :id");
        query.setParameter("id", owner_id);
        return query.getResultList();
	}

	@Override
	public void save(Department_Phone phone) throws DataAccessException {
		if(phone.getId() != null && phone.getId() <= 0) {
			phone.setId(null);
		}
		
		if(phone.getId() == null){
            this.em.persist(phone);
        } else{
            this.em.merge(phone);
        }
		
	}

	@Override
	public void deleteDepartmentPhoneById(int id) throws DataAccessException {
		if(id > 0){
            Department_Phone phone = this.findDepartmentPhoneById(id);
            
            this.em.remove(phone);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
		
	}

}
