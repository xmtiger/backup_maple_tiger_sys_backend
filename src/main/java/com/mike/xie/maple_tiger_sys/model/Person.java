package com.mike.xie.maple_tiger_sys.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class Person extends BaseEntity{

	@Column(name = "first_name")
    @NotNull
    protected String firstName;
	
	@Column(name="middle_name")
    protected String middleName;

    @Column(name = "last_name")
    @NotNull
    protected String lastName;
    
    @Column(name = "birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    protected Date birth_date;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    protected Gender gender;  //0: female, 1: male    
    
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
