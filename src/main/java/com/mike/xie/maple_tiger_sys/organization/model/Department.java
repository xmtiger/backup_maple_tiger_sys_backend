package com.mike.xie.maple_tiger_sys.organization.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mike.xie.maple_tiger_sys.model.Familyable;

import com.mike.xie.maple_tiger_sys.model.NamedEntity;
import com.mike.xie.maple_tiger_sys.organization.rest.JsonCustomDepartmentDeserializer;
import com.mike.xie.maple_tiger_sys.organization.rest.JsonCustomDepartmentSerializer;

@Entity
@Table(name = "departments")
@JsonSerialize(using = JsonCustomDepartmentSerializer.class)
@JsonDeserialize(using = JsonCustomDepartmentDeserializer.class)
public class Department extends NamedEntity implements Comparable<Department>, Familyable<Department> {
	
	/*@Column(name = "begin_time")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date begin_time;
	
	@Column(name = "end_time")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonIgnore         //to avoid json version error from ajax to controller, when coversion with json data type.
    private Date end_time;*/
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	private Set<Department_History> histories;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	private Set<Department_Address> addresses;
	
	//mappedBy means the class field name of the class 'Employee'
	//The fetch type shall be lazy in production version.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", fetch = FetchType.EAGER)
    private Set<Employee> employees;
        
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER) // the "owner" is the field "owner" of the class Phone
    private Set<Department_Phone> phones;
    
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Department_Email> emails;
    
  //The following fields are for department relationship
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "department_relationship", joinColumns = @JoinColumn(name = "id_child"),
            inverseJoinColumns = @JoinColumn(name = "id_father"))
    @JsonIgnore //to avoid infinite recursive definition actions.
    private Department father;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "department_relationship", joinColumns = @JoinColumn(name = "id_father"),
            inverseJoinColumns = @JoinColumn(name = "id_child"))
    @JsonIgnore //to avoid infinite recursive definition actions.
    private Set<Department> children;

    public void addAddress(Department_Address address) {
    	if(this.addresses == null) {
    		this.addresses = new HashSet<Department_Address>();
    	}
    	
    	this.addresses.add(address);
    	if(address.getOwner() != this) {
    		address.setOwner(this);
    	}
    }
    
    public void removeAddress(Department_Address address) {
    	
    	if(address.getOwner() == this) {
    		address.removeOwner(this);
    	}
    	this.addresses.remove(address);
    }
    
    public void addPhone(Department_Phone phone) {
    	if(this.phones == null) {
    		this.phones = new HashSet<Department_Phone>();
    	}
    	
    	this.phones.add(phone);
    	if(phone.getOwner() != this) {
    		phone.setOwner(this);
    	}
    }
    
    public void removePhone(Department_Phone phone) {
    	if(phone.getOwner() == this) {
    		phone.removeOwner(this);
    	}
    	this.phones.remove(phone);
    }
    
    public void addEmail(Department_Email email) {
    	if(this.emails == null) {
    		this.emails = new HashSet<Department_Email>();
    	}
    	
    	this.emails.add(email);
    	if(email.getOwner() != this) {
    		email.setOwner(this);
    	}
    }
    
    public void removeEmail(Department_Email email) {
    	if(email.getOwner() == this) {
    		email.removeOwner(this);
    	}
    	this.emails.remove(email);
    }
    
    public void addHistory(Department_History history) {
    	if(this.histories == null) {
    		this.histories = new HashSet<Department_History>();
    	}
    	
    	this.histories.add(history);
    	if(history.getOwner() != this) {
    		history.setOwner(this);
    	}
    }
    
    public void removeHistory(Department_History history) {
    	if(history.getOwner() == this) {
    		history.removeOwner(this);
    	}
    	this.histories.remove(history);
    }
    
    public Set<Department_History> getHistories() {
		return histories;
	}

	public void setHistories(Set<Department_History> histories) {
		this.histories = histories;
	}

	public Set<Department_Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Department_Address> addresses) {
		this.addresses = addresses;
	}

	public Set<Department_Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Department_Phone> phones) {
		this.phones = phones;
	}

	public Set<Department_Email> getEmails() {
		return emails;
	}

	public void setEmails(Set<Department_Email> emails) {
		this.emails = emails;
	}

	public Department getFather() {
        return father;
    }

    public void setFather(Department father) {
        this.father = father;
    }

    public Set<Department> getChildren() {
        return children;
    }

    public void setChildren(Set<Department> children) {
        this.children = children;
    }
    
    protected Set<Employee> getEmployeesInternal(){
        if(this.employees == null){
            this.employees = new HashSet<>();
        }
        return this.employees;
    }
    
    protected void setEmployeesInternal(Set<Employee> employees){
        this.employees = employees;
    }    
    
    public void setEmployees(Set<Employee> employees){
        this.employees = employees;
    }
    
    public Set<Employee> getEmployees(){
        return employees;
    }
    
    public void addEmployee(Employee employee){
        getEmployeesInternal().add(employee);
        employee.setDepartment(this);
    }
    
    public Employee getEmployee(String lastName){
        return getEmployee(lastName, false);
    }
    
    public Employee getEmployee(String lastName, boolean ignoreNew){
        lastName = lastName.toLowerCase();
        for (Employee employee : getEmployeesInternal()){
            if(!ignoreNew || !employee.isNew()){
                String compName = employee.getLastName();
                compName = compName.toLowerCase();
                if(compName.equals(lastName))
                    return employee;
            }
        }   
        return null;
    }
    
    
    @Override
    public int compareTo(Department t) {
        
        if(this.id.equals(t.id) || this.getName().equals(t.getName()))
            return 0;
        else
            return -1;
    }
    
    public boolean copyFrom(Department t){       
        
        this.setName(t.getName());
               
        this.setEmployees(t.getEmployees());
        
        return true;
    }
    
    @Override
    public String toString(){
        return "id: " + getId() + "; name: " + getName();                
    }
    
    @Override
    public boolean isTheFather(Department t) {
        if(t == null)
            return false;
        
        if(t.father == null)
            return false;
        
        if( t.father.getId().equals(this.getId()))
            return true;        
        
        /* the same can not be treated as unique name
        if(t.father.getName().equals(this.getName()))
            return true;
        */
        
        return false;
    }
    
    @Override
    public boolean isTheChildren(Department t) {
        if(t == null)
            return false;
        
        if(this.father == null)
            return false;
        
        
        if(this.father.getId().equals(t.getId()))
                return true;
                
        return false;
    }
}
