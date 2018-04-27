package com.mike.xie.maple_tiger_sys.organization.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mike.xie.maple_tiger_sys.model.Email;

@Entity
@Table(name = "employee_emails")
public class Employee_Email extends Email {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	protected Employee owner;
}
