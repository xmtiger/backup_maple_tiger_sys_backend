package com.mike.xie.maple_tiger_sys.organization.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

import com.mike.xie.maple_tiger_sys.model.BaseEntity;
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
	
	@RequestMapping(value = "/add/id/{departmentId}/fatherId/{fatherId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Department> addNewDepartment(@PathVariable("departmentId") int departmentId, @PathVariable("fatherId") int fatherId,
			@RequestBody @Valid Department department, BindingResult bindingResult) {
		
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (department == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Department>(headers, HttpStatus.BAD_REQUEST);
		}
		
		// Department newDepartment = new Department();
		// newDepartment.copyFrom(department);

		if(fatherId > 0) {
			Department parentDepartment = this.organizationService.findDepartmentById(fatherId);
			if(parentDepartment == null) {
				return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
			}
			department.setFather(parentDepartment);
		}
				
		this.organizationService.saveDepartment(department);
		
		return new ResponseEntity<Department>(department, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/id/{departmentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> deleteDepartment(@PathVariable("departmentId") int departmentId) {
		
		/*BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (department == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Department>(headers, HttpStatus.BAD_REQUEST);
		}*/
		
		Department currentDepartment = this.organizationService.findDepartmentById(departmentId);
		if(currentDepartment == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.organizationService.deleteDepartmentById(departmentId);
		
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/updateParent/id/{departmentId}/parentId/{parentDeptId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Department> updateDepartmentParent(@PathVariable("departmentId") int departmentId,
			@PathVariable("parentDeptId") int parentDeptId) {
		
		Department currentDepartment = this.organizationService.findDepartmentById(departmentId);
		if(currentDepartment == null) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		}
		if(parentDeptId > 0) {
			Department parentDepartment = this.organizationService.findDepartmentById(parentDeptId);
			currentDepartment.setFather(parentDepartment);
		} else {
			currentDepartment.setFather(null);
		}	
		
		boolean rs = this.organizationService.saveDepartmentWithNewParent(currentDepartment);
		if(rs == true) {
			return new ResponseEntity<Department>(currentDepartment, HttpStatus.OK);
		} else {
			return new ResponseEntity<Department>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/update/id/{departmentId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Department> updateDepartment(@PathVariable("departmentId") int departmentId, 
			@RequestBody @Valid Department department, BindingResult bindingResult) {
		
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (department == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Department>(headers, HttpStatus.BAD_REQUEST);
		}
		/* find department by id from the input department, and set the fields from the input department also
		 * Also note the owner for the filed members are also required to be set*/
		return this.organizationService.updateDepartment(department, departmentId);
		
		/*Department currentDepartment = this.organizationService.findDepartmentById(departmentId);
		if(currentDepartment == null) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		}
				
		currentDepartment.setName(department.getName());
		
		this.filter(department.getAddresses(), currentDepartment.getAddresses(), currentDepartment);
		this.filter(department.getEmails(), currentDepartment.getEmails(), currentDepartment);
		this.filter(department.getHistories(), currentDepartment.getHistories(), currentDepartment);
		this.filter(department.getPhones(), currentDepartment.getPhones(), currentDepartment);
		
		this.organizationService.saveDepartment(currentDepartment);		
		return new ResponseEntity<Department>(currentDepartment, HttpStatus.NO_CONTENT);*/
	}
	
	/*public void filter(Collection<? extends BaseEntity> setFromClient, Collection<? extends BaseEntity> setFromServer, Department currentDepartment) {
		// iterate email set to find which one is to be added or removed or revised.
		Collection<BaseEntity> departmentItemsToBeDeleted = new HashSet<BaseEntity>();
		Iterator<? extends BaseEntity> iter_currentItems = setFromServer.iterator();
        boolean ifExistingItemsEmpty = true;
		while(iter_currentItems.hasNext()) {
                        ifExistingItemsEmpty = false;
			BaseEntity currentDepartmentItem = iter_currentItems.next();
						
			Iterator<? extends BaseEntity> iter_items = setFromClient.iterator();
			boolean ifDeleted = true;
			while(iter_items.hasNext()) {
				BaseEntity itemToBeUpdatedOrAdded = iter_items.next();
						
				if(itemToBeUpdatedOrAdded.getId()<=0) {	// id<=0, it is a new email to be added
					//through setOwner, the department already put the email into its email set
					if(itemToBeUpdatedOrAdded instanceof Department_Email) {
						((Department_Email)itemToBeUpdatedOrAdded).setOwner(currentDepartment);
					} else if(itemToBeUpdatedOrAdded instanceof Department_Address) {
						((Department_Address)itemToBeUpdatedOrAdded).setOwner(currentDepartment);
					} else if (itemToBeUpdatedOrAdded instanceof Department_Phone) {
						((Department_Phone)itemToBeUpdatedOrAdded).setOwner(currentDepartment);
					} else if (itemToBeUpdatedOrAdded instanceof Department_History) {
						((Department_History)itemToBeUpdatedOrAdded).setOwner(currentDepartment);
					}
					              
					ifDeleted = false;
				}
						
				if(itemToBeUpdatedOrAdded.getId() == currentDepartmentItem.getId()) { // to be revised
					if(itemToBeUpdatedOrAdded instanceof Department_Email) {
						((Department_Email)currentDepartmentItem).copy(((Department_Email)itemToBeUpdatedOrAdded));
					} else if(itemToBeUpdatedOrAdded instanceof Department_Address) {
						((Department_Address)currentDepartmentItem).copy((Department_Address)itemToBeUpdatedOrAdded);
					} else if (itemToBeUpdatedOrAdded instanceof Department_Phone) {
						((Department_Phone)currentDepartmentItem).copy((Department_Phone)itemToBeUpdatedOrAdded);
					} else if (itemToBeUpdatedOrAdded instanceof Department_History) {
						((Department_History)currentDepartmentItem).copy((Department_History)itemToBeUpdatedOrAdded);
					}
					
					ifDeleted = false;
				}
			}
			if(ifDeleted == true) {	// to be deleted
				departmentItemsToBeDeleted.add(currentDepartmentItem);
			}
		}
        if(ifExistingItemsEmpty == true){
                    
        	// add the new items directly
            Iterator<? extends BaseEntity> iter_items = setFromClient.iterator();
            while(iter_items.hasNext()){
            	BaseEntity itemToBeAdded = iter_items.next();
                if(itemToBeAdded instanceof Department_Email) {
                	((Department_Email)itemToBeAdded).setOwner(currentDepartment);
                } else if(itemToBeAdded instanceof Department_Address) {
                	((Department_Address)itemToBeAdded).setOwner(currentDepartment);
                } else if (itemToBeAdded instanceof Department_Phone) {
                    ((Department_Phone)itemToBeAdded).setOwner(currentDepartment);
                } else if (itemToBeAdded instanceof Department_History) {
                    ((Department_History)itemToBeAdded).setOwner(currentDepartment);
                }
            }
        } else{
                    
        	setFromServer.removeAll(departmentItemsToBeDeleted);
            Iterator<? extends BaseEntity> iter_toBeDeleted = departmentItemsToBeDeleted.iterator();
            while(iter_toBeDeleted.hasNext()) {
            
            	BaseEntity itemToBeDeleted = iter_toBeDeleted.next();
                
            	if(itemToBeDeleted instanceof Department_Email) {
            		this.organizationService.deleteDepartmentEmailById(itemToBeDeleted.getId());
            	} else if(itemToBeDeleted instanceof Department_Address) {
            		this.organizationService.deleteDepartmentAddressById(itemToBeDeleted.getId());
                } else if (itemToBeDeleted instanceof Department_Phone) {
                    this.organizationService.deleteDepartmentPhoneById(itemToBeDeleted.getId());
                } else if (itemToBeDeleted instanceof Department_History) {
                    this.organizationService.deleteDepartmentHistoryById(itemToBeDeleted.getId());
                }

            }
       }		
	}*/
}
