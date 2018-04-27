package com.mike.xie.maple_tiger_sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Email extends BaseEntity {

	@Column(name = "email_type")
	protected String email_type;		//personal or office
	
	@Column(name = "email_address")
	protected String email_address;
}
