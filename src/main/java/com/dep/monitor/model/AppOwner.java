package com.dep.monitor.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "appOwner")
@Table(name = "appOwner", uniqueConstraints = @UniqueConstraint(columnNames = {"appId", "umAccount"}))
public class AppOwner {
	@Basic
	private String appId;
	
	@Basic
	private String umAccount;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getUmAccount() {
		return umAccount;
	}
	public void setUmAccount(String umAccount) {
		this.umAccount = umAccount;
	}
	
}
