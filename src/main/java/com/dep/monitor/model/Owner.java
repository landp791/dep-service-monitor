package com.dep.monitor.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "owner")
@Table(name = "owner", uniqueConstraints = @UniqueConstraint(columnNames = {"umAccount"}))
public class Owner {
    @Id
	private String umAccount;
    
    @Basic
	private String name;
    
    @Basic
	private String mail;
	
	public String getUmAccount() {
		return umAccount;
	}
	public void setUmAccount(String umAccount) {
		this.umAccount = umAccount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
}
