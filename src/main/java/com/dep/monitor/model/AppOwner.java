package com.dep.monitor.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "appOwner")
@Table(name = "app_owner", uniqueConstraints = @UniqueConstraint(columnNames = {"appUrl"}))
public class AppOwner {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic
	private String appName;
	
	@Basic
	private String appUrl;
	
	@Basic
	private String owner;
	
	public AppOwner(String appName, String appUrl, String owner) {
		this.id = id;
		this.appName = appName;
		this.appUrl = appUrl;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
}
