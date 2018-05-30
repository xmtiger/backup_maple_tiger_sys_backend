package com.mike.xie.maple_tiger_sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class NamedFile extends NamedEntity{

	@Column(name = "status")
	private String status;
	
	@Column(name = "file_path")
	private String file_path;
	
	@Column(name = "file_type")
	private String file_type;
	
	@Column(name = "upload_time")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    protected Date upload_time;
	
	@Column(name = "description")
	protected String description;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public Date getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(Date upload_time) {
		this.upload_time = upload_time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void copy(NamedFile file) {
		if(file != null) {
			this.setName(file.getName());
			this.description = file.getDescription();
			this.file_path = file.getFile_path();
			this.file_type = file.getFile_type();
			this.status = file.getStatus();
			this.upload_time = file.getUpload_time();
			
		}
	}
}
