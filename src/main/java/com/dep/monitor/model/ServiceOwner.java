package com.dep.monitor.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "serviceOwner")
@Table(name = "serviceOwner", uniqueConstraints = @UniqueConstraint(columnNames = {"serviceId", "umAccount"}))
public class ServiceOwner {
	@Basic
	private String serviceId;
	
	@Basic
	private String umAccount;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getUmAccount() {
		return umAccount;
	}
	public void setUmAccount(String umAccount) {
		this.umAccount = umAccount;
	}
	
}
