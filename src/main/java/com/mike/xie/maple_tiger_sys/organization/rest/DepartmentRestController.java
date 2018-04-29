package com.mike.xie.maple_tiger_sys.organization.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mike.xie.maple_tiger_sys.organization.model.Department;
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
		if(departments.isEmpty()) {
			return new ResponseEntity<Collection<Department>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Department>>(departments, HttpStatus.OK);
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
}
