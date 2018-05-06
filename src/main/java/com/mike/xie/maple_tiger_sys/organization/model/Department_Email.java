package com.mike.xie.maple_tiger_sys.organization.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mike.xie.maple_tiger_sys.model.Email;

@Entity
@Table(name = "department_emails")
public class Department_Email extends Email {

	@ManyToOne
	@JoinColumn(name = "owner_id")
	@JsonIgnore
	protected Department owner;
	
	public void setOwner(Department owner) {
		this.owner = owner;
		if(!owner.getEmails().contains(this)) {
			owner.getEmails().add(this);
		}
	}
	
	public Department getOwner() {
		return this.owner;
	}
	
	public void removeOwner(Department owner) {
		if(owner != null) {
			if(owner.getEmails().contains(this)) {
				owner.getEmails().remove(this);
			}
			this.owner = null;
		}
	}
}
