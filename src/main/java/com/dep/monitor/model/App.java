package com.dep.monitor.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;


@Entity(name = "app")
@Table(name = "app", uniqueConstraints = @UniqueConstraint(columnNames = {"appUrl"}))
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
	
    @Basic
	private String appName;
    
    @Basic
	private String appUrl;
    
    private int status;
    
    public App() {
    	
    }
    
    public App(String appUrl, String appName) {
    	this.appUrl = appUrl;
    	this.appName = appName;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
