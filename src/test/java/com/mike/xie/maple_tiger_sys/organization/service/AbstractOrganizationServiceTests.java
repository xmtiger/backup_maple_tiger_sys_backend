package com.mike.xie.maple_tiger_sys.organization.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Address;

public abstract class AbstractOrganizationServiceTests {

	@Autowired
	protected OrganizationService organizationService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldFindDempartmentById() {
		Department department = this.organizationService.findDepartmentById(1);
		
		assertThat(department.getName().length()>0).isTrue();
	}
	
	@Test
	public void shouldInsertDepartment() {
		String newDeptName = "construction";
		Collection<Department> departments = this.organizationService.findDepartmentByName(newDeptName);
		int found = departments.size();
		
		Department department = new Department();
		department.setName(newDeptName);
		this.organizationService.saveDepartment(department);
		
		assertThat(department.getId().intValue()).isNotEqualTo(0);
		
		Collection<Department> departments2 = this.organizationService.findDepartmentByName(newDeptName);
		assertThat(departments2.size()).isEqualTo(found +1);
		/*Department_Address address = new Department_Address();
		address.setId(4);
		address.setAddress_type("office");
		address.setApartment_number("1121");
		address.setBuilding_number("");
		address.setStreet_number("1190");
		address.setStreet_name("springcome");
		address.setCity("Toronto");
		address.setProvince_state("Ontario");
		address.setCountry("Canada");
		address.setPostcode_zip("T6R 3M2");
		address.setOwner(department);*/	
		
	}
	
	@Test
	public void shouldUpdateDepartment() {
		Department department = this.organizationService.findDepartmentById(1);
		String newName = department.getName() + "X";
		
		department.setName(newName);
		
		this.organizationService.saveDepartment(department);
		department = this.organizationService.findDepartmentById(1);
		assertThat(department.getName()).isEqualTo(newName);
	}
	
	@Test
	public void shouldDeleteDeparment() {
		String newDeptName = "construction";
		Collection<Department> departments = this.organizationService.findDepartmentByName(newDeptName);

		if(departments.size() == 0) {
			Department department = new Department();
			department.setName(newDeptName);
			this.organizationService.saveDepartment(department);
			
			departments = this.organizationService.findDepartmentByName(newDeptName);
			Iterator<Department> iter = departments.iterator();
			if(departments.size() == 1) {
				int id = iter.next().getId();
				this.organizationService.deleteDepartmentById(id);
				
				departments = this.organizationService.findDepartmentByName(newDeptName);
				assertThat(departments.size()).isEqualTo(0);
				
			}
		}
		
	}
	
}
