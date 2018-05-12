package com.mike.xie.maple_tiger_sys.organization.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Address;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Email;
import com.mike.xie.maple_tiger_sys.organization.model.Department_History;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Phone;
import com.mike.xie.maple_tiger_sys.organization.service.OrganizationService;
import com.mike.xie.maple_tiger_sys.organization.util.TreeNode;

@RestController
@RequestMapping("/api/department")
public class DepartmentRestController {

	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Department>> getDepartments() {
		Collection<Department> departments = this.organizationService.findAllDepartments();
		
		/*only collect the department at root level, for which father is null
		  because the department repeats when the department has a father;
		  Note: to check if father is null, it is only can be done at server side, 
		  because the client side does not have father due to the tag of jsonIgnore*/
		Collection<Department> outputDepartments = new HashSet<Department>();
		Iterator<Department> iter_department = departments.iterator();
		while(iter_department.hasNext()) {
			Department department = iter_department.next();
			if(department.getFather() == null) {
				outputDepartments.add(department);
			}
		}
		
		if(departments.isEmpty()) {
			return new ResponseEntity<Collection<Department>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Department>>(outputDepartments, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TreeNode<Department>> getTree() {
		TreeNode<Department> tree =  this.organizationService.findTreeFromDepartments();
		System.out.println(tree);
		return new ResponseEntity<TreeNode<Department>>(tree, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{departmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Department> getDepartmentById(@PathVariable("departmentId") int departmentId){
		Department department = null;
		department = this.organizationService.findDepartmentById(departmentId);
		if(department == null) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Department>(department, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Department>> getDepartmentByName(@PathVariable("name") String name){
		if(name == null) {
			name = "";
		}
		Collection<Department> departments = this.organizationService.findDepartmentByName(name);
		if(departments.isEmpty()) {
			return new  ResponseEntity<Collection<Department>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Department>>(departments, HttpStatus.OK);
	}

	@RequestMapping(value = "/update/id/{departmentId}/fatherId/{fatherId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Department> updateDepartment(@PathVariable("departmentId") int departmentId, 
			@PathVariable("fatherId") int fatherId, @RequestBody @Valid Department department,
			BindingResult bindingResult) {
		
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (department == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Department>(headers, HttpStatus.BAD_REQUEST);
		}
		/* find department by id from the input department, and set the fields from the input department also
		 * Also note the owner for the filed members are also required to be set*/
		Department currentDepartment = this.organizationService.findDepartmentById(departmentId);
		if(currentDepartment == null) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		}
		
		Department fatherDepartment = this.organizationService.findDepartmentById(fatherId);
		
		currentDepartment.setName(department.getName());
		
		Iterator<Department_Address> iter_addresses = department.getAddresses().iterator();
		while(iter_addresses.hasNext()) {
			iter_addresses.next().setOwner(currentDepartment);
		}
		
		Iterator<Department_Email> iter_emails = department.getEmails().iterator();
		while(iter_emails.hasNext()) {
			iter_emails.next().setOwner(currentDepartment);
		}
		
		Iterator<Department_History> iter_histories = department.getHistories().iterator();
		while(iter_histories.hasNext()) {
			iter_histories.next().setOwner(currentDepartment);
		}
		
		Iterator<Department_Phone> iter_phones = department.getPhones().iterator();
		while(iter_phones.hasNext()) {
			iter_phones.next().setOwner(currentDepartment);
		}
		
		currentDepartment.setAddresses(department.getAddresses());
		currentDepartment.setEmails(department.getEmails());
		currentDepartment.setHistories(department.getHistories());
		currentDepartment.setPhones(department.getPhones());
		if(fatherDepartment != null) {
			currentDepartment.setFather(department);
		}
		currentDepartment.setChildren(department.getChildren());
		
		this.organizationService.saveDepartment(currentDepartment);		
		return new ResponseEntity<Department>(currentDepartment, HttpStatus.NO_CONTENT);
	}
}
