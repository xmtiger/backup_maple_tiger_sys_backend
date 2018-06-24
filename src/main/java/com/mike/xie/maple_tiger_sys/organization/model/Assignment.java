package com.mike.xie.maple_tiger_sys.organization.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mike.xie.maple_tiger_sys.model.BaseEntity;

@Entity
@Table(name = "assignments")
public class Assignment extends BaseEntity {

	@Column(name = "title")
	private String title;
	
	@Column(name = "begin_time")
	private Date begin_time;
	
	@Column(name = "end_time")
	private Date end_time;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("assignments")
    private Project project;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
	if(!employee.getAssignments().contains(this)) {
            employee.getAssignments().add(this);
	}
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
        
    public void copy(Assignment assignment) {
    	if(assignment != null) {
    		this.begin_time = assignment.begin_time;
    		this.end_time = assignment.end_time;
    		this.description = assignment.description;
    		this.status = assignment.status;
    		this.title = assignment.title;
    	}
    }
}
