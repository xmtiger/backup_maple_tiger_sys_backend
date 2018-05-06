package com.mike.xie.maple_tiger_sys.organization.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mike.xie.maple_tiger_sys.model.Address;

@Entity
@Table(name = "department_addresses")
public class Department_Address extends Address {

	@ManyToOne
	@JoinColumn(name = "owner_id")
	@JsonIgnore
	protected Department owner;
	
	public void setOwner(Department owner) {
		this.owner = owner;
		if(!owner.getAddresses().contains(this)) {
			owner.getAddresses().add(this);
		}
	}
	
	public Department getOwner() {
		return this.owner;
	}
	
	public void removeOwner(Department owner) {
		if(owner != null) {
			if(owner.getAddresses().contains(this)) {
				owner.getAddresses().remove(this);
			}
			this.owner = null;
		}
	}
}
