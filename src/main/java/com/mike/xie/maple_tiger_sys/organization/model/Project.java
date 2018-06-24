package com.mike.xie.maple_tiger_sys.organization.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mike.xie.maple_tiger_sys.model.NamedEntity;

@Entity
@Table(name = "projects")
public class Project extends NamedEntity{	
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="id_parent")
    @JsonIgnore //to avoid infinite recursive definition actions.
    private Project parent;
    
    @OneToMany(mappedBy ="parent",fetch = FetchType.EAGER) 
    private Set<Project> children;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
    private Set<ChargeCode> charge_codes;
	
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<Assignment> assignments;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getParent() {
        return parent;
    }

    public void setParent(Project parent) {
        this.parent = parent;
    }

    public Set<Project> getChildren() {
        return children;
    }

    public void setChildren(Set<Project> children) {
        this.children = children;
    }

    public Set<ChargeCode> getCharge_codes() {
        return charge_codes;
    }

    public void setCharge_codes(Set<ChargeCode> charge_codes) {
        this.charge_codes = charge_codes;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }
    
    
}
