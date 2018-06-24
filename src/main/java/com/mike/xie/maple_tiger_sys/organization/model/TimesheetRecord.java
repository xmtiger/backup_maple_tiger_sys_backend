package com.mike.xie.maple_tiger_sys.organization.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mike.xie.maple_tiger_sys.model.BaseEntity;

@Entity
@Table(name = "timesheet_records")
public class TimesheetRecord extends BaseEntity{

	@Column(name = "date")
	private Date date = new Date();
	
	@Column(name = "hours")
	private byte hours;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Employee employee;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "charge_code_id")
    @JsonIgnore
    private ChargeCode chargeCode;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte getHours() {
        return hours;
    }

    public void setHours(byte hours) {
        this.hours = hours;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ChargeCode getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(ChargeCode chargeCode) {
        this.chargeCode = chargeCode;
    }
	
    
}
