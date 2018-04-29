package com.mike.xie.maple_tiger_sys.organization.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Address;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Email;
import com.mike.xie.maple_tiger_sys.organization.model.Department_History;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Phone;

public class JsonCustomDepartmentDeserializer extends StdDeserializer<Department> {

	public JsonCustomDepartmentDeserializer() {
		this(null);
	}
	
	protected JsonCustomDepartmentDeserializer(Class<Department> t) {
		super(t);
	}

	@Override
	public Department deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		
		JsonNode node = parser.getCodec().readTree(parser);
		
		Department department = new Department();
		int id = node.get("id").asInt();
		String name = node.get("name").asText(null);
		
		if(id > 0) {
			department.setId(id);
		}
		department.setName(name);
		
		JsonNode node_addresses = node.withArray("addresses");		
		Iterator<JsonNode> iter_addresses = node_addresses.iterator();
		while(iter_addresses.hasNext()) {
			Department_Address address = new Department_Address();			
			
			JsonNode node_address = iter_addresses.next();
			
			int address_id = node_address.get("id").asInt();
			String address_type = node_address.get("address_type").asText();
			String street_name = node_address.get("street_name").asText();
			String street_number = node_address.get("street_number").asText();
			String building_number = node_address.get("building_number").asText();
			String apartment_number = node_address.get("apartment_number").asText();
			String city = node_address.get("city").asText();
			String province_state = node_address.get("province_state").asText();
			String country = node_address.get("country").asText();
			String postcode_zip = node_address.get("postcode_zip").asText();
			int owner_id = node_address.get("owner").asInt();
			
			address.setId(address_id);
			address.setAddress_type(address_type);
			address.setStreet_name(street_name);
			address.setStreet_number(street_number);
			address.setBuilding_number(building_number);
			address.setApartment_number(apartment_number);
			address.setCity(city);
			address.setProvince_state(province_state);
			address.setCountry(country);
			address.setPostcode_zip(postcode_zip);
			
			if(owner_id == department.getId()) {
				address.setOwner(department);
				department.addAddress(address);
			}			
		}
		
		JsonNode node_phones = node.withArray("phones");		
		Iterator<JsonNode> iter_phones = node_phones.iterator();
		while(iter_phones.hasNext()) {
			Department_Phone phone = new Department_Phone();			
			
			JsonNode node_phone = iter_phones.next();
			
			int phone_id = node_phone.get("id").asInt();
			String phone_type = node_phone.get("phone_type").asText();
			String area_code = node_phone.get("area_code").asText();
			String phone_number = node_phone.get("phone_number").asText();			
			int owner_id = node_phone.get("owner").asInt();
			
			phone.setId(phone_id);
			phone.setPhone_type(phone_type);
			phone.setArea_code(area_code);
			phone.setPhone_number(phone_number);
			
			if(owner_id == department.getId()) {
				phone.setOwner(department);
				department.addPhone(phone);
			}			
		}
		
		JsonNode node_emails = node.withArray("emails");		
		Iterator<JsonNode> iter_emails = node_emails.iterator();
		while(iter_emails.hasNext()) {
			Department_Email email = new Department_Email();			
			
			JsonNode node_email = iter_emails.next();
			
			int email_id = node_email.get("id").asInt();
			String email_type = node_email.get("email_type").asText();
			String email_address = node_email.get("email_address").asText();
						
			int owner_id = node_email.get("owner").asInt();
			
			email.setId(email_id);
			email.setEmail_type(email_type);
			email.setEmail_address(email_address);
			
			if(owner_id == department.getId()) {
				email.setOwner(department);
				department.addEmail(email);
			}			
		}	
		
		JsonNode node_histories = node.withArray("histories");		
		Iterator<JsonNode> iter_histories = node_histories.iterator();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		while(iter_histories.hasNext()) {
			Department_History history = new Department_History();			
			
			JsonNode node_history = iter_histories.next();
			
			int history_id = node_history.get("id").asInt();
			String status = node_history.get("status").asText();
			String str_begin_time = node_history.get("begin_time").asText(null);
			String str_end_time = node_history.get("end_time").asText(null);
			String description = node_history.get("description").asText();
			
			int owner_id = node_history.get("owner").asInt();
			
			history.setId(history_id);
			history.setStatus(status);;
			history.setDescription(description);
			
			try {
				Date begin_time = formatter.parse(str_begin_time);
				Date end_time = formatter.parse(str_end_time);
				
				history.setBegin_time(begin_time);
				history.setEnd_time(end_time);
				
			} catch(ParseException e) {
				e.printStackTrace();
				throw new IOException(e);
			}			
			
			if(owner_id == department.getId()) {
				history.setOwner(department);
				department.addHistory(history);
			}			
		}
		
		return department;
	}

}
