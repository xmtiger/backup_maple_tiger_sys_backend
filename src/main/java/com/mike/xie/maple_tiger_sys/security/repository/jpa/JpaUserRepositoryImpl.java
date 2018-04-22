package com.mike.xie.maple_tiger_sys.security.repository.jpa;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mike.xie.maple_tiger_sys.security.model.User;
import com.mike.xie.maple_tiger_sys.security.repository.UserRepository;

@Repository
@Profile("jpa")
public class JpaUserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<User> findByName(String username) {
		
		Query query = this.em.createQuery("SELECT user from User user left join fetch user.roles WHERE user.username =:username");
		query.setParameter("username", username);
		
		return  Optional.of((User)query.getSingleResult());
	}
	
}
