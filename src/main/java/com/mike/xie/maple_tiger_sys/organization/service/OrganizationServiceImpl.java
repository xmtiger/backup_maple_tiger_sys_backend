package com.mike.xie.maple_tiger_sys.organization.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mike.xie.maple_tiger_sys.model.BaseEntity;
import com.mike.xie.maple_tiger_sys.model.NamedFile;
import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Address;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Email;
import com.mike.xie.maple_tiger_sys.organization.model.Department_History;
import com.mike.xie.maple_tiger_sys.organization.model.Department_Phone;
import com.mike.xie.maple_tiger_sys.organization.model.Employee;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Address;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Email;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_File;
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
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_FileRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_HistoryRepository;
import com.mike.xie.maple_tiger_sys.organization.repository.Employee_PhoneRepository;
import com.mike.xie.maple_tiger_sys.organization.service.file.StorageService;
import com.mike.xie.maple_tiger_sys.organization.service.file.UploadFileResponse;
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
	@Autowired
	private Employee_FileRepository employeeFileRepository;
	
	@Autowired
    private StorageService fileStorageService;
	
	@Autowired
	private Environment env;
	
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
		
		if(department.getId() != null && department.getId() <= 0) {
			department.setId(null);
		}
		
		if(department.getId() == null) {
			//save department first, then the id can be made for the realted tables to use as owner_id		
			departmentRepository.save(department);
		}		
		
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
		
        if(department.getId() != null){
        	departmentRepository.save(department);
        }
	}
	
	@Override
	@Transactional
	public void deleteDepartmentAddressById(int id) throws DataAccessException {
		this.departmentAddressRepository.deleteDepartmentAddressById(id);
	}
	
	@Override
	@Transactional
	public void deleteDepartmentPhoneById(int id) throws DataAccessException {
		this.departmentPhoneRepository.deleteDepartmentPhoneById(id);
	}
	
	@Override
	@Transactional
	public void deleteDepartmentHistoryById(int id) throws DataAccessException {
		this.departmentHistoryRepository.deleteDepartmentHistoryById(id);
	}
	
	/*@Override
	@Transactional
	public void saveNewDepartmentEmail(Department_Email email) throws DataAccessException {
		this.departmentEmailRepository.save(email);
	}*/

	@Override
	@Transactional
	public void deleteDepartmentEmailById(int id) throws DataAccessException {
		this.departmentEmailRepository.deleteDepartmentEmailById(id);
	}
	
	@Override
	@Transactional
	public void deleteDepartmentById(int id) throws DataAccessException {
		/*note: before deleting department, make sure that the subordinate objects like employees shall be deleted first
		*/
		departmentRepository.deleteDepartmentById(id);
	}
	
	@Override
	@Transactional
	public ResponseEntity<Department> updateDepartment(Department department, int id) throws DataAccessException {
		Department currentDepartment = this.findDepartmentById(id);
		if(currentDepartment == null) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		}
				
		currentDepartment.setName(department.getName());
		
		this.filterDepartmentInfo(department.getAddresses(), currentDepartment.getAddresses(), currentDepartment);
		this.filterDepartmentInfo(department.getEmails(), currentDepartment.getEmails(), currentDepartment);
		this.filterDepartmentInfo(department.getHistories(), currentDepartment.getHistories(), currentDepartment);
		this.filterDepartmentInfo(department.getPhones(), currentDepartment.getPhones(), currentDepartment);
		
		this.saveDepartment(currentDepartment);		
		return new ResponseEntity<Department>(currentDepartment, HttpStatus.NO_CONTENT);
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
	@Transactional(readOnly = true)
	public Employee_File findEmployeeFileById(int id) throws DataAccessException {
		return employeeFileRepository.findEmployeeFileById(id);
	}

	@Override
	@Transactional
	public void saveEmployee(Employee employee) throws DataAccessException {
		//save employee first, then the id can be made for the realted tables to use as owner_id
        if(employee.getId() == null || employee.getId() <= 0){
        	employeeRepository.save(employee);
        }		
		
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
                
                if(employee.getId() > 0){
                    employeeRepository.save(employee);
                }
	}

	@Override
	@Transactional
	public void deleteEmployeeById(int id) throws DataAccessException {
		/*before deleting the employee, make sure that the subordinate objects shall be deleted first
		  if required to delete other objects, use the related repository class to do the deletion */
				
		employeeRepository.deleteEmployeeById(id);
		
	}
	
	@Override
	@Transactional
	public void deleteEmployeeEmailById(int id) throws DataAccessException {
		this.employeeEmailRepository.deleteEmployeeEmailById(id);
	}
	
	@Override
	@Transactional
	public void deleteEmployeeAddressById(int id) throws DataAccessException {
		this.employeeAddressRepository.deleteEmployeeAddressById(id);
	}
	
	@Override
	@Transactional
	public void deleteEmployeePhoneById(int id) throws DataAccessException {
		this.employeePhoneRepository.deleteEmployeePhoneById(id);
	}
	
	@Override
	@Transactional
	public void deleteEmployeeHistoryById(int id) throws DataAccessException {
		this.employeeHistoryRepository.deleteEmployeeHistoryById(id);
	}
	
	@Override
	@Transactional
	public void deleteEmployeeFileById(int id) throws DataAccessException{
		this.employeeFileRepository.deleteEmployeeFileById(id);
	}
	
	@Override
	@Transactional
	public ResponseEntity<Employee> updateEmployee(Employee employee, int id) throws DataAccessException {
		Employee currentEmployee = this.findEmployeeById(id);
		if(currentEmployee == null) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
				
		currentEmployee.setFirstName(employee.getFirstName());
		currentEmployee.setMiddleName(employee.getMiddleName());
		currentEmployee.setLastName(employee.getLastName());
		currentEmployee.setBirth_date(employee.getBirth_date());
		currentEmployee.setGender(employee.getGender());
		currentEmployee.setStatus(employee.getStatus());
		
		this.filterEmployeeInfo(employee.getAddresses(), currentEmployee.getAddresses(), currentEmployee);
		this.filterEmployeeInfo(employee.getEmails(), currentEmployee.getEmails(), currentEmployee);
		this.filterEmployeeInfo(employee.getHistories(), currentEmployee.getHistories(), currentEmployee);
		this.filterEmployeeInfo(employee.getPhones(), currentEmployee.getPhones(), currentEmployee);
		this.filterEmployeeInfo(employee.getFiles(), currentEmployee.getFiles(), currentEmployee);
		
		this.saveEmployee(currentEmployee);		
		return new ResponseEntity<Employee>(currentEmployee, HttpStatus.NO_CONTENT);
	}

	public void filterEmployeeInfo(Collection<? extends BaseEntity> setFromClient, Collection<? extends BaseEntity> setFromServer, Employee currentEmployee) {
		// iterate email set to find which one is to be added or removed or revised.
		Collection<BaseEntity> itemsToBeDeleted = new HashSet<BaseEntity>();
		Iterator<? extends BaseEntity> iter_currentItems = setFromServer.iterator();
        boolean ifExistingItemsEmpty = true;
		while(iter_currentItems.hasNext()) {
                        ifExistingItemsEmpty = false;
			BaseEntity currentItem = iter_currentItems.next();
						
			Iterator<? extends BaseEntity> iter_items = setFromClient.iterator();
			boolean ifDeleted = true;
			while(iter_items.hasNext()) {
				BaseEntity itemToBeUpdatedOrAdded = iter_items.next();
						
				if(itemToBeUpdatedOrAdded.getId()<=0) {	// id<=0, it is a new email to be added
					//through setOwner, the department already put the email into its email set
					if(itemToBeUpdatedOrAdded instanceof Employee_Email) {
						((Employee_Email)itemToBeUpdatedOrAdded).setOwner(currentEmployee);
					} else if(itemToBeUpdatedOrAdded instanceof Employee_Address) {
						((Employee_Address)itemToBeUpdatedOrAdded).setOwner(currentEmployee);
					} else if (itemToBeUpdatedOrAdded instanceof Employee_Phone) {
						((Employee_Phone)itemToBeUpdatedOrAdded).setOwner(currentEmployee);
					} else if (itemToBeUpdatedOrAdded instanceof Employee_History) {
						((Employee_History)itemToBeUpdatedOrAdded).setOwner(currentEmployee);
					} else if (itemToBeUpdatedOrAdded instanceof Employee_File) {
						((Employee_File)itemToBeUpdatedOrAdded).setOwner(currentEmployee);
					}
					              
					ifDeleted = false;
				}
						
				if(itemToBeUpdatedOrAdded.getId() == currentItem.getId()) { // to be revised
					if(itemToBeUpdatedOrAdded instanceof Employee_Email) {
						((Employee_Email)currentItem).copy(((Employee_Email)itemToBeUpdatedOrAdded));
					} else if(itemToBeUpdatedOrAdded instanceof Employee_Address) {
						((Employee_Address)currentItem).copy((Employee_Address)itemToBeUpdatedOrAdded);
					} else if (itemToBeUpdatedOrAdded instanceof Employee_Phone) {
						((Employee_Phone)currentItem).copy((Employee_Phone)itemToBeUpdatedOrAdded);
					} else if (itemToBeUpdatedOrAdded instanceof Employee_History) {
						((Employee_History)currentItem).copy((Employee_History)itemToBeUpdatedOrAdded);
					} else if (itemToBeUpdatedOrAdded instanceof Employee_File) {
						((Employee_File)currentItem).copy((Employee_File)itemToBeUpdatedOrAdded);
					}
					
					ifDeleted = false;
				}
			}
			if(ifDeleted == true) {	// to be deleted
				itemsToBeDeleted.add(currentItem);
			}
		}
        if(ifExistingItemsEmpty == true){
                    
        	// add the new items directly
            Iterator<? extends BaseEntity> iter_items = setFromClient.iterator();
            while(iter_items.hasNext()){
            	BaseEntity itemToBeAdded = iter_items.next();
                if(itemToBeAdded instanceof Employee_Email) {
                	((Employee_Email)itemToBeAdded).setOwner(currentEmployee);
                } else if(itemToBeAdded instanceof Employee_Address) {
                	((Employee_Address)itemToBeAdded).setOwner(currentEmployee);
                } else if (itemToBeAdded instanceof Employee_Phone) {
                    ((Employee_Phone)itemToBeAdded).setOwner(currentEmployee);
                } else if (itemToBeAdded instanceof Employee_History) {
                    ((Employee_History)itemToBeAdded).setOwner(currentEmployee);
                } else if ((itemToBeAdded) instanceof Employee_File) {
                	((Employee_File)itemToBeAdded).setOwner(currentEmployee);
                }
            }
        } else{
                    
        	setFromServer.removeAll(itemsToBeDeleted);
            Iterator<? extends BaseEntity> iter_toBeDeleted = itemsToBeDeleted.iterator();
            while(iter_toBeDeleted.hasNext()) {
            
            	BaseEntity itemToBeDeleted = iter_toBeDeleted.next();
                
            	if(itemToBeDeleted instanceof Employee_Email) {
            		this.deleteEmployeeEmailById(itemToBeDeleted.getId());
            	} else if(itemToBeDeleted instanceof Employee_Address) {
            		this.deleteEmployeeAddressById(itemToBeDeleted.getId());
                } else if (itemToBeDeleted instanceof Employee_Phone) {
                    this.deleteEmployeePhoneById(itemToBeDeleted.getId());
                } else if (itemToBeDeleted instanceof Employee_History) {
                    this.deleteEmployeeHistoryById(itemToBeDeleted.getId());
                } else if (itemToBeDeleted instanceof Employee_File) {
                	this.deleteEmployeeFileById(itemToBeDeleted.getId());
                }

            }
       }		
	}
	
	public void filterDepartmentInfo(Collection<? extends BaseEntity> setFromClient, Collection<? extends BaseEntity> setFromServer, Department currentDepartment) {
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
            		this.deleteDepartmentEmailById(itemToBeDeleted.getId());
            	} else if(itemToBeDeleted instanceof Department_Address) {
            		this.deleteDepartmentAddressById(itemToBeDeleted.getId());
                } else if (itemToBeDeleted instanceof Department_Phone) {
                    this.deleteDepartmentPhoneById(itemToBeDeleted.getId());
                } else if (itemToBeDeleted instanceof Department_History) {
                    this.deleteDepartmentHistoryById(itemToBeDeleted.getId());
                }

            }
       }		
	}
	
	@Override
	@Transactional
	public ResponseEntity<NamedFile> uploadFile(int employeeId, MultipartFile inputFile) throws DataAccessException {
		
		// find the employee with the input id.
		Employee employee = this.findEmployeeById(employeeId);
		
		String inputFilename = inputFile.getOriginalFilename();
		String inputFileExtension = "";
		int i = inputFilename.lastIndexOf('.');
		if (i > 0) {
			inputFileExtension = inputFilename.substring(i+1);
		}
		
		// new file name is table name 'employee' + the employee id + record id
		int fileIndex = employee.getFiles().size() + 1;
		String newFileName = "employee_" + employeeId + "_" + fileIndex + "." + inputFileExtension;
		
		// save the file into the folder on the server
        String employeeFileFolderPath = env.getProperty("storage.employeeFolderPath");
		this.fileStorageService.initWithPath(employeeFileFolderPath);    			
        String filename = fileStorageService.storeWithNewFileName(inputFile, newFileName);        
        
        // generate a new record associated with this new file
        Employee_File newFile = new Employee_File();
        Date uploadDate = new Date();
        newFile.setUpload_time(uploadDate);
        newFile.setName(inputFilename);        
        
        //the file type can be image or word or pdf, etc.
        String fileType = inputFile.getContentType();
        if(fileType.contains("image")) {
        	newFile.setFile_type("image");
        }
        String filePath = "employee/files/" + employeeId + "/" + filename;
        newFile.setFile_path(filePath);       
        newFile.setOwner(employee);
        this.employeeFileRepository.save(newFile);      
        
        return new ResponseEntity<NamedFile>(newFile, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<Void> deleteEmployeeFile(int fileId) throws DataAccessException {
		Employee_File file = this.findEmployeeFileById(fileId);
		if(file == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}	  	
        
    	this.deleteEmployeeFileById(fileId);
    	    	
    	String filePath = file.getFile_path();
        //int index = filePath.lastIndexOf("/");
        //String filename = filePath.substring(index + 1);
        
        Path path = Paths.get(filePath);
        String filename = path.getFileName().toString();
        
        String employeeFileFolderPath = env.getProperty("storage.employeeFolderPath");
        this.fileStorageService.initWithPath(employeeFileFolderPath);
        
    	this.fileStorageService.deleteFile(filename);
    	
    	return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
}
