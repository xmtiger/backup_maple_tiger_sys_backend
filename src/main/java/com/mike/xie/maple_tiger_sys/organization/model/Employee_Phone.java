package com.mike.xie.maple_tiger_sys.organization.model;

import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mike.xie.maple_tiger_sys.model.Phone;

@Entity
@Table(name = "employee_phones")
public class Employee_Phone extends Phone{
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	protected Employee owner;
	
	public void setOwner(Employee owner) {
		this.owner = owner;
		if(!owner.getPhones().contains(this)) {
			owner.getPhones().add(this);
		}
	}
	
	public Employee getOwner() {
		return this.owner;
	}
	
	public void removeOwner(Employee owner) {
		if(owner != null) {
			if(owner.getPhones().contains(this)) {
				owner.getPhones().remove(this);
			}
			this.owner = null;
		}
	}
}
