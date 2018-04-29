package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Employee;
import com.mike.xie.maple_tiger_sys.organization.repository.EmployeeRepository;

@Repository
@Profile("jpa")
public class EmployeeRepositoryImpl implements EmployeeRepository {
	
	@PersistenceContext
    private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Employee> findEmployeeByLastName(String lastName) throws DataAccessException {
		Query query = this.em.createQuery("SELECT employee FROM Employee employee WHERE employee.lastName LIKE :lastName");
        query.setParameter("lastName", lastName + "%");
        return query.getResultList();
	}

	@Override
	public Employee findEmployeeById(int id) throws DataAccessException {
		//Query query = this.em.createQuery("SELECT employee FROM Employee employee left join fetch employee.department employee.id = :id", Employee.class);
        //query.setParameter("id", id);
        //return (Employee)query.getSingleResult();
        return this.em.find(Employee.class, id);
	}

	@Override
	public void save(Employee employee) throws DataAccessException {
		if(employee.getId() == null){
            this.em.persist(employee);
        }else{
            this.em.merge(employee);
        }
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Employee> findEmployeesByDepartmentId(int deptId) throws DataAccessException {
		Query query = this.em.createQuery("SELECT v FROM Employee v where v.department.id= :id");
        query.setParameter("id", deptId);
        return query.getResultList();
	}

	@Override
	public void deleteEmployeeById(int id) throws DataAccessException {
		if(id > 0 ) {			
			// Employee employee = this.findEmployeeById(id);
			// this.em.remove(employee);
			
			String str_id = String.valueOf(id);
			this.em.createQuery("DELETE FROM Employee_Address v WHERE owner_id=" + str_id).executeUpdate();
			this.em.createQuery("DELETE FROM Employee_Email v WHERE owner_id=" + str_id).executeUpdate();
			this.em.createQuery("DELETE FROM Employee_Phone v WHERE owner_id=" + str_id).executeUpdate();
			
			this.em.createQuery("DELETE FROM Employee v WHERE id=" + str_id).executeUpdate();
		}
		
	}

}
