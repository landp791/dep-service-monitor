package com.dep.monitor.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.Id;

@Entity(name = "service")
@Table(name = "service", uniqueConstraints = @UniqueConstraint(columnNames = {"serviceId"}))
public class Service {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private String serviceId;
	
    @Basic
	private String serviceName;
    
    @Basic
	private String serviceUrl;
    
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
}
