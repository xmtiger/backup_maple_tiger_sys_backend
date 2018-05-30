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

	public String getPhone_type() {
		return phone_type;
	}

	public void setPhone_type(String phone_type) {
		this.phone_type = phone_type;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public void copy(Phone phone) {
		if(phone != null) {
			this.phone_type = phone.phone_type;
			this.area_code = phone.area_code;
			this.phone_number = phone.phone_number;
		}
	}
}
