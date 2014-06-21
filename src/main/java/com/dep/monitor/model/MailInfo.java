package com.dep.monitor.model;

import java.util.ArrayList;
import java.util.List;

public class MailInfo {

	private List<String> goodUrls;
	
	private List<String> badUrls;	
	
	private List<String> toMailAddrs;
	
	private String content;
	
	private String type;
	
	public List<String> getGoodUrls() {
		return goodUrls;
	}

	public void setGoodUrls(List<String> goodUrls) {
		this.goodUrls = goodUrls;
	}

	public List<String> getBadUrls() {
		return badUrls;
	}

	public void setBadUrls(List<String> badUrls) {
		this.badUrls = badUrls;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void addGoodUrl(String url) {
		if (goodUrls == null) {
			goodUrls = new ArrayList<String>();
		}
		goodUrls.add(url);
	}
	
	public void addBadUrl(String url) {
		if (badUrls == null) {
			badUrls = new ArrayList<String>();
		}
		badUrls.add(url);
	}

	public List<String> getToMailAddrs() {
		return toMailAddrs;
	}

	public void setToMailAddrs(List<String> toMailAddrs) {
		this.toMailAddrs = toMailAddrs;
	}
	
	
}
