package com.mike.xie.maple_tiger_sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Address extends BaseEntity {
	
	@Column(name = "address_type")
	protected String address_type;

	@Column(name = "street_name")
	@NotNull
    protected String street_name;
	
	@Column(name = "street_number")
	protected String street_number;
	
	@Column(name = "building_number")
	protected String building_number;
	
	@Column(name = "apartment_number")
	protected String apartment_number;
	
	@Column(name = "city")
	protected String city;
	
	@Column(name = "province_state")	//for Canada and US
	protected String province_state;
	
	@Column(name = "country")
	protected String country;
	
	@Column(name = "postcode_zip")
	protected String postcode_zip;

	public String getAddress_type() {
		return address_type;
	}

	public void setAddress_type(String address_type) {
		this.address_type = address_type;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public String getStreet_number() {
		return street_number;
	}

	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}

	public String getBuilding_number() {
		return building_number;
	}

	public void setBuilding_number(String building_number) {
		this.building_number = building_number;
	}

	public String getApartment_number() {
		return apartment_number;
	}

	public void setApartment_number(String apartment_number) {
		this.apartment_number = apartment_number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince_state() {
		return province_state;
	}

	public void setProvince_state(String province_state) {
		this.province_state = province_state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostcode_zip() {
		return postcode_zip;
	}

	public void setPostcode_zip(String postcode_zip) {
		this.postcode_zip = postcode_zip;
	}
	
	public void copy(Address address) {
		if(address != null) {
			this.address_type = address.address_type;
			this.street_name = address.street_name;
			this.street_number = address.street_number;
			this.apartment_number = address.apartment_number;
			this.building_number = address.building_number;
			this.city = address.city;
			this.country = address.country;
			this.postcode_zip = address.postcode_zip;
			this.province_state = address.province_state;			
		}
	}
}
