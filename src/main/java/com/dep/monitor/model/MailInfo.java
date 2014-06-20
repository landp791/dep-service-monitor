package com.dep.monitor.model;

import java.util.List;

public class MailInfo {

	private String url;
	
	private List<String> toMailAddrs;
	
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getToMailAddrs() {
		return toMailAddrs;
	}

	public void setToMailAddrs(List<String> toMailAddrs) {
		this.toMailAddrs = toMailAddrs;
	}
	
	
}
