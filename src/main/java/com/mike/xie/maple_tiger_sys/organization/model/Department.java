package com.mike.xie.maple_tiger_sys.organization.model;

import java.util.HashSet;
import java.util.Iterator;
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

import com.mike.xie.maple_tiger_sys.model.Familyable;

import com.mike.xie.maple_tiger_sys.model.NamedEntity;
import javax.persistence.Column;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "departments")
//@JsonSerialize(using = JsonCustomDepartmentSerializer.class)
//@JsonDeserialize(using = JsonCustomDepartmentDeserializer.class)
public class Department extends NamedEntity implements Comparable<Department>, Familyable<Department> {
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	private Set<Department_History> histories;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	private Set<Department_Address> addresses;	
        
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER) // the "owner" is the field "owner" of the class Phone
    private Set<Department_Phone> phones;
    
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Department_Email> emails;
    
    /*@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Department_File> files;*/
    
  //The following fields are for department relationship
    @ManyToOne
    @JoinColumn(name = "id_parent")
    /*@JoinTable(name = "department_relationship", joinColumns = @JoinColumn(name = "id_child"),
            inverseJoinColumns = @JoinColumn(name = "id_father"))*/
    @JsonIgnore //to avoid infinite recursive definition actions.
    private Department father;
    
    @OneToMany(mappedBy = "father",fetch = FetchType.EAGER)
    /*@JoinTable(name = "department_relationship", joinColumns = @JoinColumn(name = "id_father"),
            inverseJoinColumns = @JoinColumn(name = "id_child"))*/    
    private Set<Department> children;
    
  //mappedBy means the class field name of the class 'Employee'
  	//The fetch type shall be lazy in production version.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", fetch = FetchType.EAGER)
    private Set<Employee> employees;

    /*public Set<Department_File> getFiles() {
		return files;
	}

	public void setFiles(Set<Department_File> files) {
		this.files = files;
	}
	
	public void addFile(Department_File file) {
		if(this.files == null) {
			this.files = new HashSet<Department_File>();
		}
		
		this.files.add(file);
		if(file.getOwner() != null) {
			file.setOwner(this);
		}
	}
	
	public void removeFile(Department_File file) {
		if(file.getOwner() == this) {
			file.removeOwner(this);
		}
		this.files.remove(file);
	}*/
    
    public void addChild(Department department) {
    	if(this.children == null) {
    		this.children = new HashSet<Department>();
    	}
    	if(department != null) {
    		Iterator<Department> iter = this.children.iterator();
    		boolean bIfExist = false;
    		while(iter.hasNext()) {
    			Department dept = iter.next();
    			if(dept.getId() == department.getId()) {
    				bIfExist = true;
    				break;
    			}
    		}
    		if(bIfExist == false) {
    			this.children.add(department);
    		}
    	}
    	
    }

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
    	if(this.father != null) {
    		this.father.children.remove(this);
    	}
    	
        this.father = father;
        if(father != null) {
        	if(!father.getChildren().contains(this)) {
        		father.children.add(this);
        	}        	
        }
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
        
    	this.setId(t.getId());
        this.setName(t.getName());
        this.setAddresses(t.getAddresses());
        this.setEmails(t.getEmails());
        this.setHistories(t.getHistories());
        this.setPhones(t.getPhones());
        this.setFather(t.getFather());
        this.setChildren(t.getChildren());
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
