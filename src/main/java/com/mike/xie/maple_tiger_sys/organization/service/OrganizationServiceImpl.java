package com.mike.xie.maple_tiger_sys.organization.service;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Address;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Email;
import com.mike.xie.maple_tiger_sys.organization.model.Department_History;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Phone;
import com.mike.xie.maple_tiger_sys.organization.model.Employee;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Address;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Email;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_History;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Phone;
import com.mike.xie.maple_tiger_sys.organization.repository.DepartmentRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Department_AddressRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Department_EmailRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Department_HistoryRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Department_PhoneRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.EmployeeRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_AddressRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_EmailRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_HistoryRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_PhoneRepository;
import com.mike.xie.maple_tiger_sys.organization.util.TreeNode;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private Department_AddressRepository departmentAddressRepository;	
	@Autowired
	private Department_PhoneRepository departmentPhoneRepository;
	@Autowired
	private Department_EmailRepository departmentEmailRepository;
	@Autowired
	private Department_HistoryRepository departmentHistoryRepository;
	
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private Employee_AddressRepository employeeAddressRepository;	
	@Autowired
	private Employee_PhoneRepository employeePhoneRepository;
	@Autowired
	private Employee_EmailRepository employeeEmailRepository;
	@Autowired
	private Employee_HistoryRepository employeeHistoryRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Department findDepartmentById(int id) throws DataAccessException {
		return departmentRepository.findDepartmentById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Department> findAllDepartments() throws DataAccessException {
		return departmentRepository.findAllDepartments();
	}
	
	@Override
	@Transactional(readOnly = true)
	public TreeNode<Department> findTreeFromDepartments() throws DataAccessException {
		
		TreeNode<Department> tree = new TreeNode<>();
		Collection<Department>departments = this.departmentRepository.findAllDepartments();
		Iterator<Department> iter_departments = departments.iterator();
		while(iter_departments.hasNext()) {
			Department department = iter_departments.next();
			TreeNode<Department> treeNode = new TreeNode<>(department);
			
			tree.addNode1(treeNode);
		}
		
		if(tree.getChildren().size() == 1 && tree.getData() == null) {
			return tree.getChildren().get(0);
		}else {
			return tree;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Department> findDepartmentByName(String name) throws DataAccessException {
		return departmentRepository.findDepartmentByName(name);
	}	

	@Override
	@Transactional
	public void saveDepartment(Department department) throws DataAccessException {
		//save department first, then the id can be made for the realted tables to use as owner_id		
		departmentRepository.save(department);
		
		//Then, save or update the subordinate addresses
		Collection<Department_Address> addresses = department.getAddresses();
		if(addresses != null && department.getId() > 0) {
			Iterator<Department_Address> iter_address = addresses.iterator();
			while(iter_address.hasNext()) {
				this.departmentAddressRepository.save(iter_address.next());
			}
		}
		//Then save or update the subordinate phones
		Collection<Department_Phone> phones = department.getPhones();
		if(phones != null && department.getId() > 0) {
			Iterator<Department_Phone> iter_phone = phones.iterator();
			while(iter_phone.hasNext()) {
				this.departmentPhoneRepository.save(iter_phone.next());
			}
		}
		//Then save or update the subordinate emails
		Collection<Department_Email> emails = department.getEmails();
		if(emails != null && department.getId() > 0) {
			Iterator<Department_Email> iter_email = emails.iterator();
			while(iter_email.hasNext()) {
				this.departmentEmailRepository.save(iter_email.next());
			}
		}
		//Then save or update the subordinate histories
		Collection<Department_History> histories = department.getHistories();
		if(histories != null && department.getId() > 0) {
			Iterator<Department_History> iter_history = histories.iterator();
			while(iter_history.hasNext()) {
				this.departmentHistoryRepository.save(iter_history.next());
			}
		}
	}

	@Override
	@Transactional
	public void deleteDepartmentById(int id) throws DataAccessException {
		/*note: before deleting department, make sure that the subordinate objects like employees shall be deleted first
		*/
		departmentRepository.deleteDepartmentById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Employee findEmployeeById(int id) throws DataAccessException {
		return employeeRepository.findEmployeeById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Employee> findEmployeeByDepartmentId(int deptId) throws DataAccessException {
		return employeeRepository.findEmployeesByDepartmentId(deptId);
	}

	@Override
	@Transactional
	public void saveEmployee(Employee employee) throws DataAccessException {
		//save employee first, then the id can be made for the realted tables to use as owner_id
		employeeRepository.save(employee);
		
		//Then, save or update the subordinate addresses
		Collection<Employee_Address> addresses = employee.getAddresses();
		if(addresses != null && employee.getId() > 0) {
			Iterator<Employee_Address> iter_address = addresses.iterator();
			while(iter_address.hasNext()) {
				this.employeeAddressRepository.save(iter_address.next());
			}
		}
				
		//Then save or update the subordinate phones
		Collection<Employee_Phone> phones = employee.getPhones();
		if(phones != null && employee.getId() > 0) {
			Iterator<Employee_Phone> iter_phone = phones.iterator();
			while(iter_phone.hasNext()) {
				this.employeePhoneRepository.save(iter_phone.next());
			}
		}
		//Then save or update the subordinate emails
		Collection<Employee_Email> emails = employee.getEmails();
		if(emails != null && employee.getId() > 0) {
			Iterator<Employee_Email> iter_email = emails.iterator();
			while(iter_email.hasNext()) {
				this.employeeEmailRepository.save(iter_email.next());
			}
		}
		//Then save or update the subordinate histories
		Collection<Employee_History> histories = employee.getHistories();
		if(histories != null && employee.getId() > 0) {
			Iterator<Employee_History> iter_history = histories.iterator();
			while(iter_history.hasNext()) {
				this.employeeHistoryRepository.save(iter_history.next());
			}
		}
	}

	@Override
	@Transactional
	public void deleteEmployeeById(int id) throws DataAccessException {
		/*before deleting the employee, make sure that the subordinate objects shall be deleted first
		  if required to delete other objects, use the related repository class to do the deletion */
				
		employeeRepository.deleteEmployeeById(id);
		
	}

}
