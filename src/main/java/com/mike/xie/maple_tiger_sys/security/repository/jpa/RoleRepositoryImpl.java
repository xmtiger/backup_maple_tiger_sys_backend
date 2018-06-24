package com.mike.xie.maple_tiger_sys.security.repository.jpa;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mike.xie.maple_tiger_sys.security.model.Role;
import com.mike.xie.maple_tiger_sys.security.model.User;
import com.mike.xie.maple_tiger_sys.security.repository.RoleRepository;

@Repository
@Profile("jpa")
public class RoleRepositoryImpl implements RoleRepository{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public Role findRoleById(int id) throws DataAccessException {
		return em.find(Role.class, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Role findRoleByName(String name) throws DataAccessException {
		Query query = this.em.createQuery("SELECT DISTINCT role from Role role WHERE role.name =:name");
		query.setParameter("name", name);
		
		return  (Role)query.getSingleResult();
	}
	
	@Override
	@Transactional
	public void save(Role role) throws DataAccessException {
		if(role.getId() != null && role.getId() <= 0) {
			role.setId(null);
		}
		
		if(role.getId() == null ){
            this.em.persist(role);
        } else{
            this.em.merge(role);
        }
		
	}
	
	@Override
	@Transactional
	public void deleteRoleById(int id) throws DataAccessException {
		if(id > 0){
			Role role = this.findRoleById(id);
            
            this.em.remove(role);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}
}
