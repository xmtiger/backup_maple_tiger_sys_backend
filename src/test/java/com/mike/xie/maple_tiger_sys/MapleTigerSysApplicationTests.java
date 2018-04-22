package com.mike.xie.maple_tiger_sys;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.mike.xie.maple_tiger_sys.MapleTigerSysApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes=MapleTigerSysApplication.class)
public class MapleTigerSysApplicationTests {

	@LocalServerPort
	private int port;

	private TestRestTemplate template = new TestRestTemplate();

	/*@Test
	public void homePageLoads() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void userEndpointProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/user", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}*/
	
	//note the following test is failed at the request of "/resource", then redirect to '/', which is '/index.html'
	//so, the final return code is 200. But this request is failed at the authentication test.
	/*@Test
	public void resourceEndpointProtected() {
		System.out.println("--------------------test get resource request1-------------------------");
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/resource", String.class);
		System.out.println("--------------------test get resource request2-------------------------");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		System.out.println("--------------------test get resource request3-------------------------");
	}*/
	
	
	public void printBcryptPassword(String password) {
		BCryptPasswordEncoder passwordEncorder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncorder.encode(password);
		
		System.out.println("********************Hashed password: " + hashedPassword);
		
	}

	@Test
	public void loginSucceeds() {
		String username = "user01";
		String password = "user01";
		TestRestTemplate template2 = new TestRestTemplate(username, password);
		
		ResponseEntity<String> response = template2.getForEntity("http://localhost:" + port
				+ "/user", String.class);
		this.printBcryptPassword(password);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}

}
