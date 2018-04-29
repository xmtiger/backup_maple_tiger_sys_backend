package com.mike.xie.maple_tiger_sys.organization.model;

import java.util.HashSet;
import java.util.Set;

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
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER) // the "owner" is the field "owner" of the class Phone
    private Set<Employee_Phone> phones;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	private Set<Employee_Email> emails;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)	
	private Set<Employee_Address> addresses;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	private Set<Employee_History> histories;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")	// this is the foreign key of the user table, which contains security information
	@NotFound(action=NotFoundAction.IGNORE)	//an employee may not have a credential user account 
	private User user;
	
	public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
        if(! this.department.getEmployees().contains(this)) {
        	this.department.addEmployee(this);
        }
    } 
    
    public void addHistory(Employee_History history) {
    	if(this.histories == null) {
    		this.histories = new HashSet<Employee_History>();
    	}
    	
    	this.histories.add(history);
    	if(history.getOwner() != this) {
    		history.setOwner(this);
    	}
    }
    
    public void removeHistory(Employee_History history) {
    	if(history.getOwner() == this) {
    		history.removeOwner(this);
    	}
    	this.histories.remove(history);
    }
    
    public Set<Employee_History> getHistories() {
		return histories;
	}

	public void setHistories(Set<Employee_History> histories) {
		this.histories = histories;
	}
    
    public void addPhone(Employee_Phone phone) {
    	if(this.phones == null) {
    		this.phones = new HashSet<Employee_Phone>();
    	}
    	
    	this.phones.add(phone);
    	if(phone.getOwner() != this) {
    		phone.setOwner(this);
    	}
    }
    
    public void removePhone(Employee_Phone phone) {
    	if(phone.getOwner() == this) {
    		phone.removeOwner(this);
    	}
    	this.phones.remove(phone);
    }
    
    public void addEmail(Employee_Email email) {
    	if(this.emails == null) {
    		this.emails = new HashSet<Employee_Email>();
    	}
    	
    	this.emails.add(email);
    	if(email.getOwner() != this) {
    		email.setOwner(this);
    	}
    }
    
    public void removeEmail(Employee_Email email) {
    	if(email.getOwner() == this) {
    		email.removeOwner(this);
    	}
    	this.emails.remove(email);
    }
    
    public void addAddress(Employee_Address address) {
    	if(this.addresses == null) {
    		this.addresses = new HashSet<Employee_Address>();
    	}
    	
    	this.addresses.add(address);
    	if(address.getOwner() != this) {
    		address.setOwner(this);
    	}
    }
    
    public void removeAddress(Employee_Address address) {
    	
    	if(address.getOwner() == this) {
    		address.removeOwner(this);
    	}
    	this.addresses.remove(address);
    }
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Employee_Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Employee_Phone> phones) {
		this.phones = phones;
	}

	public Set<Employee_Email> getEmails() {
		return emails;
	}

	public void setEmails(Set<Employee_Email> emails) {
		this.emails = emails;
	}

	public Set<Employee_Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Employee_Address> addresses) {
		this.addresses = addresses;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
