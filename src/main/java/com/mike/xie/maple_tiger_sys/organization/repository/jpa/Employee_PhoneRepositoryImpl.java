package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_Phone;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_PhoneRepository;

@Repository
@Profile("jpa")
public class Employee_PhoneRepositoryImpl implements Employee_PhoneRepository {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Employee_Phone findEmployeePhoneById(int id) throws DataAccessException {
		return em.find(Employee_Phone.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Employee_Phone> findEmployeePhonesByOwnerId(int owner_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Employee_Phone v where v.owner.id= :id");
        query.setParameter("id", owner_id);
        return query.getResultList();
	}

	@Override
	public void save(Employee_Phone phone) throws DataAccessException {
		if(phone.getId() == null){
            this.em.persist(phone);
        } else{
            this.em.merge(phone);
        }
		
	}

	@Override
	public void deleteEmployeePhoneById(int id) throws DataAccessException {
		if(id > 0){
            Employee_Phone phone = this.findEmployeePhoneById(id);
            
            this.em.remove(phone);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

}
