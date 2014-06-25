package com.dep.monitor.model;

import java.util.List;

public class AppOwnerView {
	
	private String appName;
	
	private String appUrl;
	
	private String toMail;
	
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

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public void setTomail(List<Owner> owners) {
		
	}
}

