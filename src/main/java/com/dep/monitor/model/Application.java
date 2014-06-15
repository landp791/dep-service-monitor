package com.dep.monitor.model;

import java.util.List;

public class Application {

	
	private String url;
	
	private List<AppOwner> owners;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<AppOwner> getOwners() {
		return owners;
	}

	public void setOwners(List<AppOwner> owners) {
		this.owners = owners;
	}
	
}
