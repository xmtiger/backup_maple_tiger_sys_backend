package com.mike.xie.maple_tiger_sys.security.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mike.xie.maple_tiger_sys.security.repository.RoleRepository;
import com.mike.xie.maple_tiger_sys.security.repository.UserRepository;
import com.mike.xie.maple_tiger_sys.model.BaseEntity;
import com.mike.xie.maple_tiger_sys.organization.model.Assignment;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Address;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Email;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_File;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_History;
import com.mike.xie.maple_tiger_sys.organization.model.Employee_Phone;
import com.mike.xie.maple_tiger_sys.security.model.Role;
import com.mike.xie.maple_tiger_sys.security.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> optionalUser = userRepository.findByName(username);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("username not found"));
		
		return CustomUserFactory.create(optionalUser.get());
	}
	
	public User findUserByUsername(String username) throws DataAccessException {
		Optional<User> optionalUser = userRepository.findByName(username);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			return null;
		}
	}
	
	public ResponseEntity<User> updateUser(User user, String originalUsername) throws DataAccessException {
		Optional<User> optionalUser = userRepository.findByName(originalUsername);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("username not found"));
		
		User curUser = optionalUser.get();
		// first check if the new username has duplicates in the database
		if(originalUsername != user.getUsername()) {
			// the username was changed at client side
			Optional<User> tmpUser = userRepository.findByName(user.getUsername());
			if(tmpUser.isPresent()) {
				return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);	// the username cannot be duplicated
			}			
		}
		
		user.setId(curUser.getId()); // set id for update
		// 
		/*Set<Role> curRoles = new HashSet<Role>();
		
		Set<Role> roles = user.getRoles();
		Iterator<Role> iter_roles = roles.iterator();
		while(iter_roles.hasNext()) {
			Role role = iter_roles.next();
			String roleName = role.getName();
			
			Role curRole = roleRepository.findRoleByName(roleName);
			
			curRoles.add(curRole);
		}
		user.setRoles(curRoles);*/
		
		this.filterUserInfo(user.getRoles(), curUser.getRoles(), user);
		//user.setRoles(curUser.getRoles());
		userRepository.save(user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	public void filterUserInfo(Collection<? extends BaseEntity> setFromClient, Collection<? extends BaseEntity> setFromServer, User user) {
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
						
				if(itemToBeUpdatedOrAdded.getId()<=0) {	// id<=0, it is a new item to be added
					//through addUser
					if(itemToBeUpdatedOrAdded instanceof Role) {
						((Role)itemToBeUpdatedOrAdded).addUser(user);;
					}
					              
					ifDeleted = false;
				}
						
				if(itemToBeUpdatedOrAdded.getId() == currentItem.getId()) { // to be revised
					if(itemToBeUpdatedOrAdded instanceof User) {
						((Role)currentItem).copy(((Role)itemToBeUpdatedOrAdded));
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
                if(itemToBeAdded instanceof Role) {
                	((Role)itemToBeAdded).addUser(user);
                }
            }
        } else{
                    
        	setFromServer.removeAll(itemsToBeDeleted);
            // roles can not be deleted from the database. Remove roles from the current user
       }
	}
}
