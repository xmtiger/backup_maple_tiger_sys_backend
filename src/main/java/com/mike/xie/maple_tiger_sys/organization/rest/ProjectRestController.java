package com.mike.xie.maple_tiger_sys.organization.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mike.xie.maple_tiger_sys.organization.model.Project;
import com.mike.xie.maple_tiger_sys.organization.service.OrganizationService;

@RestController
@RequestMapping("/api/project")
public class ProjectRestController {
	
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Project>> getProjects() {
		Collection<Project> projects = this.organizationService.findAllProjects();
		
		/*only collect the department at root level, for which father is null
		  because the department repeats when the department has a father;
		  Note: to check if father is null, it is only can be done at server side, 
		  because the client side does not have father due to the tag of jsonIgnore*/
		Collection<Project> outputProjects = new HashSet<Project>();
		Iterator<Project> iter_project = projects.iterator();
		while(iter_project.hasNext()) {
			Project project = iter_project.next();
			if(project.getParent() == null) {
				outputProjects.add(project);
			}
		}
		
		if(outputProjects.isEmpty()) {
			return new ResponseEntity<Collection<Project>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Project>>(outputProjects, HttpStatus.OK);
	}

}
