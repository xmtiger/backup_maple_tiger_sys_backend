package com.mike.xie.maple_tiger_sys.security.repository.jpa;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mike.xie.maple_tiger_sys.organization.model.Department_Email;
import com.mike.xie.maple_tiger_sys.organization.util.GeneralUtils;
import com.mike.xie.maple_tiger_sys.security.model.User;
import com.mike.xie.maple_tiger_sys.security.repository.UserRepository;

@Repository
@Profile("jpa")
public class JpaUserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public User findUserById(int id) throws DataAccessException {
		return em.find(User.class, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<User> findByName(String username) {
		
		Query query = this.em.createQuery("SELECT user from User user left join fetch user.roles WHERE user.username =:username");
		query.setParameter("username", username);
		
		return  Optional.of((User)query.getSingleResult());
	}
	
	@Override
	public Collection<User> findUsersByName(String username) {
		Query query = this.em.createQuery("SELECT user from User user left join fetch user.roles WHERE user.username =:username");
		query.setParameter("username", username);
		
		return  (Collection<User>)query.getResultList();
	}
	
	@Override
    @Transactional
	public void save(User user) throws DataAccessException {
		if(user.getId() != null && user.getId() <= 0) {
			user.setId(null);
		}
		
		String bcryptPassword = GeneralUtils.createBcryptPassword(user.getPassword());
		user.setPassword(bcryptPassword);
		
		if(user.getId() == null ){
            this.em.persist(user);
        } else{
            this.em.merge(user);
        }
		
	}
	
	@Override
	@Transactional
	public void deleteUserById(int id) throws DataAccessException {
		if(id > 0){
            User user = this.findUserById(id);
            
            this.em.remove(user);
            //Query query = this.em.createQuery("DELETE FROM Department dept Where dept.id = :id");
            //int delCount = query.setParameter("id", deptId).executeUpdate();
        }
		
	}
}
