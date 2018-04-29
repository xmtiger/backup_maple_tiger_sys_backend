package com.mike.xie.maple_tiger_sys.organization.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.mike.xie.maple_tiger_sys.organization.model.Department;
import com.mike.xie.maple_tiger_sys.organization.service.ApplicationTestConfig;
import com.mike.xie.maple_tiger_sys.organization.service.OrganizationService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
@WebAppConfiguration
public class DepartmentRestControllerTests {

	@Autowired
	private DepartmentRestController departmentRestController;
	
	/*Note: the service can be either mock service (to get data from mock source)
	  or real service (to get data from database). */
	//@MockBean
	//@Autowired
	//private OrganizationService organizationService;
	
	private MockMvc mockMvc;
	
	private Department department;
	
	@Before
	public void initDepartment() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(departmentRestController)
				.setControllerAdvice(new ExceptionControllerAdvice()).build();
		
		department = new Department();
		department.setId(1);
		department.setName("Managerment");		
	}
	
	@Test
	public void testGetDepartmentById() throws Exception {
		/*Note: Instead of using mock service, we can use the practical service which
		  can extract data from the database*/
		//given(this.organizationService.findDepartmentById(1)).willReturn(department);
		this.mockMvc.perform(get("/api/department/id/1")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Managerment"));			
	}
	
	@Test
	public void testGetTreeFromDepartments() throws Exception {
		this.mockMvc.perform(get("/api/department/tree").accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
	}
}
