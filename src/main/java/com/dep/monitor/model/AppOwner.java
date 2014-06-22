package com.dep.monitor.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;

@Entity(name = "appOwner")
@Table(name = "appOwner", uniqueConstraints = @UniqueConstraint(columnNames = {"appId", "umAccount"}))
public class AppOwner {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic
	private long appId;
	
	@Basic
	private String umAccount;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public String getUmAccount() {
		return umAccount;
	}
	public void setUmAccount(String umAccount) {
		this.umAccount = umAccount;
	}
	
}
