package com.mike.xie.maple_tiger_sys.organization.rest;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mike.xie.maple_tiger_sys.model.BaseEntity;
import com.mike.xie.maple_tiger_sys.model.NamedFile;
import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.model.Employee;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Address;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Email;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_File;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_History;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Phone;
import com.mike.xie.maple_tiger_sys.organization.service.OrganizationService;
import com.mike.xie.maple_tiger_sys.organization.service.file.StorageService;
import com.mike.xie.maple_tiger_sys.organization.service.file.UploadFileResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);
	 
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
    private StorageService fileStorageService;
	
	@Autowired
	private Environment env;
	
	@RequestMapping(value = "/id/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") int employeeId){
		Employee employee = null;
		employee = this.organizationService.findEmployeeById(employeeId);
		if(employee == null) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add/id/{employeeId}/fatherId/{fatherId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Employee> addNewEmployee(@PathVariable("employeeId") int employeeId, @PathVariable("fatherId") int fatherId,
			@RequestBody @Valid Employee employee, BindingResult bindingResult) {
		
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (employee == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
		}

		if(fatherId > 0) {
			Department parentDepartment = this.organizationService.findDepartmentById(fatherId);
			if(parentDepartment == null) {
				return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
			}
			employee.setDepartment(parentDepartment);
		}
				
		this.organizationService.saveEmployee(employee);
		
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/id/{employeeId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") int employeeId) {
				
		Employee currentEmployee = this.organizationService.findEmployeeById(employeeId);
		if(currentEmployee == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.organizationService.deleteEmployeeById(employeeId);
		
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/update/id/{employeeId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Employee> updateEmployee(@PathVariable("employeeId") int employeeId, 
			@RequestBody @Valid Employee employee, BindingResult bindingResult) {
		
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (employee == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
		}
		/* find department by id from the input department, and set the fields from the input department also
		 * Also note the owner for the filed members are also required to be set*/
		return this.organizationService.updateEmployee(employee, employeeId);
		/*Employee currentEmployee = this.organizationService.findEmployeeById(employeeId);
		if(currentEmployee == null) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
				
		currentEmployee.setFirstName(employee.getFirstName());
		currentEmployee.setMiddleName(employee.getMiddleName());
		currentEmployee.setLastName(employee.getLastName());
		currentEmployee.setBirth_date(employee.getBirth_date());
		currentEmployee.setGender(employee.getGender());
		currentEmployee.setStatus(employee.getStatus());
		
		this.filter(employee.getAddresses(), currentEmployee.getAddresses(), currentEmployee);
		this.filter(employee.getEmails(), currentEmployee.getEmails(), currentEmployee);
		this.filter(employee.getHistories(), currentEmployee.getHistories(), currentEmployee);
		this.filter(employee.getPhones(), currentEmployee.getPhones(), currentEmployee);
		this.filter(employee.getFiles(), currentEmployee.getFiles(), currentEmployee);
		
		//currentDepartment.setChildren(department.getChildren());
		
		this.organizationService.saveEmployee(currentEmployee);		
		return new ResponseEntity<Employee>(currentEmployee, HttpStatus.NO_CONTENT);*/
	}	
	
	// upload employee photoes
    @RequestMapping(value = "/files/upload", method = RequestMethod.POST)
    public ResponseEntity<NamedFile> uploadFile(@RequestParam("employeeId") int employeeId, 
            @RequestParam("file") MultipartFile file) {
       
    	return this.organizationService.uploadFile(employeeId, file);
    }
        
    @RequestMapping(value = "/files/{employeeId}/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable("employeeId") int employeeId, 
    		@PathVariable("fileName") String filename, HttpServletRequest request){
		
    	// Load file as Resource
        String filePath = filename;
        String employeeFileFolderPath = env.getProperty("storage.employeeFolderPath");
        this.fileStorageService.initWithPath(employeeFileFolderPath);
        
        Resource resource = this.fileStorageService.loadAsResource(filePath);
        
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @RequestMapping(value = "/files/delete/{fileId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> deleteFile(@PathVariable("fileId") int fileId) {
    	
    	return this.organizationService.deleteEmployeeFile(fileId);
    	
    }
     
}
