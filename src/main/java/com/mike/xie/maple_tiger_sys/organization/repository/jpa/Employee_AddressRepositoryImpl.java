package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_Address;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_AddressRepository;

@Repository
@Profile("jpa")
public class Employee_AddressRepositoryImpl implements Employee_AddressRepository{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Employee_Address findEmployeeAddressById(int id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT DISTINCT address FROM Employee_Address address WHERE address.id = :id");
        query.setParameter("id", id);
        return (Employee_Address) query.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Employee_Address> findEmployeeAddressesByOwnerId(int owner_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Employee_Address v where v.owner.id= :id");
        query.setParameter("id", owner_id);
        return query.getResultList();
	}

	@Override
	public void save(Employee_Address address) throws DataAccessException {
		if(address.getId() != null && address.getId() <= 0) {
			address.setId(null);
		}
		
		if(address.getId() == null){
            this.em.persist(address);
        } else{
            this.em.merge(address);
        }
		
	}

	@Override
	public void deleteEmployeeAddressById(int id) throws DataAccessException {
		if(id > 0){
            Employee_Address address = this.findEmployeeAddressById(id);
            
            this.em.remove(address);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

}
