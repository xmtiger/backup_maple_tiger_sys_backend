package com.mike.xie.maple_tiger_sys.security.controller;

import java.security.Principal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mike.xie.maple_tiger_sys.organization.model.Employee;
import com.mike.xie.maple_tiger_sys.organization.rest.BindingErrorsResponse;
import com.mike.xie.maple_tiger_sys.security.model.User;
import com.mike.xie.maple_tiger_sys.security.service.CustomUserDetailsService;

@Controller
public class SecurityController {
	
	@Autowired
	public CustomUserDetailsService userService;
	
	//@GetMapping("/user")
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Principal user(Principal user) {
		//return user.getName();
		return user;
    }

    @GetMapping("/resource")
    @ResponseBody
    public Map<String, Object> home() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
    
    @RequestMapping(value = "/api/user/update/username/{username}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> updateUser(@PathVariable("username") String username, 
			@RequestBody @Valid User user, BindingResult bindingResult) {
		
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (user == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<User>(headers, HttpStatus.BAD_REQUEST);
		}
		
		User user1 = this.userService.findUserByUsername(user.getUsername());
		User user2 = this.userService.findUserByUsername(username);
		if(user1.getId() != user2.getId()) {
			headers.add("errors", "duplicated username");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		
		return this.userService.updateUser(user, username);
		
    }
}
