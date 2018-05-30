package com.mike.xie.maple_tiger_sys.organization.service.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
	
    private String rootLocation;
    
    private String employeeFolderPath;

    public String getRootLocation() {
        return rootLocation;
    }

    public void setRootLocation(String location) {
        this.rootLocation = location;
    }
    
    public String getEmployeeFolderPath() {
    	return this.employeeFolderPath;
    }

    public void setEmployeeFolderPath(String path) {
    	this.employeeFolderPath = path;
    }
}
