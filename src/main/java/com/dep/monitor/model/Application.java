package com.dep.monitor.model;

import java.util.List;

public class Application {

	
	private String url;
	
	private List<Owner> owners;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Owner> getOwners() {
		return owners;
	}

	public void setOwners(List<Owner> owners) {
		this.owners = owners;
	}
	
}
