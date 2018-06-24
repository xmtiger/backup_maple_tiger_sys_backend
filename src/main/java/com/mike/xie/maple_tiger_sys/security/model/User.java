package com.mike.xie.maple_tiger_sys.security.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mike.xie.maple_tiger_sys.model.BaseEntity;
import com.mike.xie.maple_tiger_sys.security.model.Role;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;*/
	
	@Column(name = "username", length = 100, unique = true)
	@NotNull
	@Size(min = 6, max = 50)
	protected String username;
	
	@Column(name = "password", length = 100)
	@NotNull
	@Size(min=6, max = 100)
	private String password;
	
	@Column(name = "first_name")
	private String first_name;
	
	@Column(name = "last_name")
	private String last_name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "enabled")
	private int enabled;
	
	@Column(name = "last_password_reset_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date last_password_reset_date = new Date();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Date getLast_password_reset_date() {
		return last_password_reset_date;
	}

	public void setLast_password_reset_date(Date last_password_reset_date) {
		this.last_password_reset_date = last_password_reset_date;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean addRole(Role role) {
		Iterator<Role> iter_roles = this.roles.iterator();
		while(iter_roles.hasNext()) {
			Role curRole = iter_roles.next();
			
			if(curRole.getName() == role.getName()) {
				return false;
			}
		}
		this.roles.add(role);
		return true;
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
}
