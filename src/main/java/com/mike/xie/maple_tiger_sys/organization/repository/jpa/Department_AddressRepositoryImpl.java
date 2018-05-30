package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Department_Address;
import com.mike.xie.maple_tiger_sys.organization.repository.Department_AddressRepository;

@Repository
@Profile("jpa")
public class Department_AddressRepositoryImpl implements Department_AddressRepository {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Department_Address findDepartmentAddressById(int id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT DISTINCT address FROM Department_Address address WHERE address.id = :id");
        query.setParameter("id", id);
        return (Department_Address) query.getSingleResult();
	}

	@Override
	public void save(Department_Address address) throws DataAccessException {
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
	public void deleteDepartmentAddressById(int id) throws DataAccessException {
		if(id > 0){
            Department_Address address = this.findDepartmentAddressById(id);
            
            this.em.remove(address);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Department_Address> findDepartmentAddressesByOwnerId(int owner_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Department_Address v where v.owner.id= :id");
        query.setParameter("id", owner_id);
        return query.getResultList();
	}

}
