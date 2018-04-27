package com.mike.xie.maple_tiger_sys.organization.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.repository.DepartmentRepository;

@Repository
@Profile("jpa")
public class DepartmentRepositoryImpl implements DepartmentRepository {

	@PersistenceContext
    private EntityManager em;
	
	/*Note: The Selection sentence shows "Department" (rather than "Departments"), 
	  which I think is the Class name of the Class "Department" rather than the table name "departments" */
	@Override
	public Department findDepartmentById(int id) throws DataAccessException {
		Query query = this.em.createQuery("SELECT DISTINCT dept FROM Department dept left join fetch dept.employees WHERE dept.id = :id");
        query.setParameter("id", id);
        return (Department) query.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Department> findDepartmentByName(String name) throws DataAccessException {
		Query query = this.em.createQuery("SELECT dept FROM Department dept left join fetch dept.employees WHERE dept.name = :name");
        query.setParameter("name", name);
        return query.getResultList();
	}

	@Override
	public void save(Department department) throws DataAccessException {
		
		if(department.getId() == null){
            this.em.persist(department);
        } else{
            this.em.merge(department);
        }
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Department> findAllDepartments() throws DataAccessException {
		
		Query query = this.em.createQuery("SELECT dept FROM Department dept");
        return query.getResultList();
	}

	@Override
	public void deleteDepartmentById(int deptId) throws DataAccessException {
		if(deptId > 0){
            Department dept = this.findDepartmentById(deptId);
            
            this.em.remove(dept);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}

}
