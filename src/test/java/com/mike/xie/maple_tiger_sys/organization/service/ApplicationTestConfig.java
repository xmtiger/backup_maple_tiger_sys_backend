package com.mike.xie.maple_tiger_sys.organization.service;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class ApplicationTestConfig {
	
	public ApplicationTestConfig() {
		MockitoAnnotations.initMocks(this);
	}

}
