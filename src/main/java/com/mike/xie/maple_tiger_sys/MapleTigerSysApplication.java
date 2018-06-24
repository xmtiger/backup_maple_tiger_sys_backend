package com.mike.xie.maple_tiger_sys;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.hsqldb.util.DatabaseManagerSwing;

@SpringBootApplication
public class MapleTigerSysApplication {

	public static void main(String[] args) {
		/* when using SpringApplication.run(..), it is ok for ApplicationTest, 
		but it has error when starting hsqldb swing manager;
		when using the SpringApplicationBuild, it is ok to run hsqldb swing manager, 
		but the applicationTest has error */
		
		// SpringApplication.run(MapleTigerSysApplication.class, args);	//for testing and production
		
		//for starting up the hsqldb swing manager, while the Application testing can not be passed.
		SpringApplicationBuilder builder = new SpringApplicationBuilder(MapleTigerSysApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}
	
	@PostConstruct
	public void startDbManager() {
		
		//hsqldb		
		DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:test", "--user", "sa", "--password", "" });

		//derby
		//DatabaseManagerSwing.main(new String[] { "--url", "jdbc:derby:memory:test", "--user", "", "--password", "" });

		//h2
		//DatabaseManagerSwing.main(new String[] { "--url", "jdbc:h2:mem:test", "--user", "sa", "--password", "" });
		
	}
}
