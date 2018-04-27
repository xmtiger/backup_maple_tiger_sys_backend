package com.mike.xie.maple_tiger_sys.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Phone extends BaseEntity {

	@Column(name = "phone_type")
	protected String phone_type;		// personal, office
	
	@Column(name = "area_code")
	protected String area_code;
	
	@Column(name = "phone_number")
	protected String phone_number;
}
