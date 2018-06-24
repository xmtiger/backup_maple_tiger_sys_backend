package com.mike.xie.maple_tiger_sys.security.model;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mike.xie.maple_tiger_sys.model.BaseEntity;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;*/
	
	@Column(name = "name")
	@NotNull
	private String name;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<User> users;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonIgnore
	public boolean isNew() {
		return this.id == null;
	}*/
	
	@Override
	public String toString() {
		return this.name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public boolean addUser(User user) {
		Iterator<User> iter_users = users.iterator();
		while(iter_users.hasNext()) {
			User curUser = iter_users.next();
			if(curUser.getUsername() == user.getUsername()) {
				return false;
			}
		}
		
		this.users.add(user);
		return true;
	}
	
	public void copy(Role role) {
		this.name = role.name;
	}
}
