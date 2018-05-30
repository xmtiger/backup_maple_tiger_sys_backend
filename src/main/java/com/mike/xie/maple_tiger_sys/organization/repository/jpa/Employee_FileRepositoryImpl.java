package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Employee_File;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_FileRepository;

@Repository
@Profile("jpa")
public class Employee_FileRepositoryImpl implements Employee_FileRepository {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Employee_File findEmployeeFileById(int id) throws DataAccessException {
		return em.find(Employee_File.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Employee_File> finalEmployeeFilesByOwnerId(int owner_id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Employee_File v where v.owner.id= :id");
        query.setParameter("id", owner_id);
        return query.getResultList();
	}

	@Override
	public void save(Employee_File employeeFile) throws DataAccessException {
		if(employeeFile.getId() != null && employeeFile.getId() <= 0) {
			employeeFile.setId(null);
		}
		
		if(employeeFile.getId() == null){
            this.em.persist(employeeFile);
        } else{
            this.em.merge(employeeFile);
        }
		
	}

	@Override
	public void deleteEmployeeFileById(int id) throws DataAccessException {
		if(id > 0){
            Employee_File file = this.findEmployeeFileById(id);
            
            this.em.remove(file);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

}
