package com.mike.xie.maple_tiger_sys.organization.rest;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Address;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Email;
import com.mike.xie.maple_tiger_sys.organization.model.Department_History;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Phone;

public class JsonCustomDepartmentSerializer extends StdSerializer<Department> {

	public JsonCustomDepartmentSerializer() {
		this(null);
	}
	
	public JsonCustomDepartmentSerializer(Class<Department> t) {
		super(t);
	}

	@Override
	public void serialize(Department department, JsonGenerator gen, SerializerProvider provider) throws IOException {
		Format formatter = new SimpleDateFormat("yyyy/MM/dd");
		gen.writeStartObject();			// department
		if(department.getId() == null) {
			gen.writeNullField("id");
		}else {
			gen.writeNumberField("id", department.getId());
		}
		
		gen.writeStringField("name", department.getName());
		
		//write department address array
		if(department.getAddresses() != null) {
			gen.writeArrayFieldStart("addresses");
			for(Department_Address address : department.getAddresses()) {
				gen.writeStartObject();		//address
				if(address.getId() == null) {
					gen.writeNullField("id");
				}else {
					gen.writeNumberField("id", address.getId());
				}
				
				gen.writeStringField("address_type", address.getAddress_type());
				gen.writeStringField("street_name", address.getStreet_name());
				gen.writeStringField("street_number", address.getStreet_number());
				gen.writeStringField("building_number", address.getBuilding_number());
				gen.writeStringField("apartment_number", address.getApartment_number());
				gen.writeStringField("city", address.getCity());
				gen.writeStringField("province_state", address.getProvince_state());
				gen.writeStringField("country", address.getCountry());
				gen.writeStringField("postcode_zip", address.getPostcode_zip());	
				gen.writeNumberField("owner", address.getOwner().getId());
				gen.writeEndObject();	//address
			}
			gen.writeEndArray();	//addresses
		}
		
		if(department.getPhones() != null) {
			gen.writeArrayFieldStart("phones");		//phones
			for(Department_Phone phone : department.getPhones()) {
				gen.writeStartObject();	//phone
				if(phone.getId() == null) {
					gen.writeNullField("id");
				}else {
					gen.writeNumberField("id", phone.getId());
				}
				
				gen.writeStringField("phone_type", phone.getPhone_type());
				gen.writeStringField("area_code", phone.getArea_code());
				gen.writeStringField("phone_number", phone.getPhone_number());
				gen.writeNumberField("owner", phone.getOwner().getId());
				gen.writeEndObject();	//phone
			}
			gen.writeEndArray();		//phones
		}
		
		if(department.getEmails() != null) {
			gen.writeArrayFieldStart("emails");	//emails
			for(Department_Email email : department.getEmails()) {
				gen.writeStartObject();	//email
				if(email.getId() == null) {
					gen.writeNullField("id");
				}else {
					gen.writeNumberField("id", email.getId());
				}
				
				gen.writeStringField("email_type", email.getEmail_type());
				gen.writeStringField("email_address", email.getEmail_address());
				gen.writeNumberField("owner", email.getOwner().getId());
				gen.writeEndObject();	//email
			}
			gen.writeEndArray();	//emails
		}
		
		if(department.getHistories() != null) {
			gen.writeArrayFieldStart("histories"); 	//hsitories
			for(Department_History history : department.getHistories()) {
				gen.writeStartObject();	//history
				if(history.getId() == null) {
					gen.writeNullField("id");
				}else {
					gen.writeNumberField("id", history.getId());
				}
				
				gen.writeStringField("status", history.getStatus());
				gen.writeStringField("begin_time", formatter.format(history.getBegin_time()));
				gen.writeStringField("end_time", formatter.format(history.getEnd_time()));
				gen.writeStringField("description", history.getDescription());
				gen.writeNumberField("owner", history.getOwner().getId());
				gen.writeEndObject();	//history
			}
			gen.writeEndArray();	//history
		}
		gen.writeEndObject(); 		//department
	}
}
