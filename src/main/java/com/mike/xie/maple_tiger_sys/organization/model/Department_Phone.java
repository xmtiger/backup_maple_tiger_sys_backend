package com.mike.xie.maple_tiger_sys.organization.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mike.xie.maple_tiger_sys.model.Phone;

@Entity
@Table(name = "department_phones")
public class Department_Phone extends Phone {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	protected Department owner;
}
