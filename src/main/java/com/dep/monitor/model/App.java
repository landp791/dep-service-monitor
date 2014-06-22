package com.dep.monitor.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;


@Entity(name = "service")
@Table(name = "service", uniqueConstraints = @UniqueConstraint(columnNames = {"appUrl"}))
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appId; 
	
    @Basic
	private String appName;
    
    @Basic
	private String appUrl;
    
    private int status;
    
    public App() {
    	
    }
    
    public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	
}
