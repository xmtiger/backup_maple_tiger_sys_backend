package com.mike.xie.maple_tiger_sys.organization.model;

import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mike.xie.maple_tiger_sys.model.Address;

@Entity
@Table(name = "employee_addresses")
public class Employee_Address extends Address {
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	protected Employee owner;
	
	public void setOwner(Employee owner) {
		this.owner = owner;
		if(!owner.getAddresses().contains(this)) {
			owner.getAddresses().add(this);
		}
	}
	
	public Employee getOwner() {
		return this.owner;
	}
	
	public void removeOwner(Employee owner) {
		if(owner != null) {
			if(owner.getAddresses().contains(this)) {
				owner.getAddresses().remove(this);
			}
			this.owner = null;
		}
	}
}
