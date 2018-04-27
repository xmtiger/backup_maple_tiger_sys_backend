package com.mike.xie.maple_tiger_sys.organization.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.mike.xie.maple_tiger_sys.model.Familyable;
import com.mike.xie.maple_tiger_sys.model.Person;

import com.mike.xie.maple_tiger_sys.security.model.User;

@Entity
@Table(name = "employees")
public class Employee extends Person implements Comparable<Employee>, Familyable<Employee> {
	
	@Column(name = "status")
	private String status;			//active or retired or laid off, etc..
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;
	
	@OneToMany(mappedBy = "owner") // the "owner" is the field "owner" of the class Phone
    private List<Employee_Phone> phones;
	
	@OneToMany(mappedBy = "owner")
	private List<Employee_Email> emails;
	
	@OneToMany(mappedBy = "owner")	
	private List<Employee_Address> addresses;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")	// this is the foreign key of the user table, which contains security information
	@NotFound(action=NotFoundAction.IGNORE)	//an employee may not have a credential user account 
	private User user;
	
	public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    } 
    
    @Override
    public int compareTo(Employee t) {
        if(this.id.equals(t.id))
            return 0;
        
        return -1;
        
    }

    @Override
    public boolean isTheFather(Employee t) {
        return false;
        
    }

    @Override
    public boolean isTheChildren(Employee t) {
        return false;
    }
}
