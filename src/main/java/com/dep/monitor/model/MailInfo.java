package com.dep.monitor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class MailInfo {

	private List<String> goodUrls;
	
	private List<String> badUrls;	
	
	private String[] toMailAddrs;
	
	private Map<String, String> appNames = Maps.newHashMap();
	
	private String content;
	
	private String type;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

	public Map<String, String> getAppNames() {
		return appNames;
	}

	public void setAppName(String appUrl, String appName) {
		this.appNames.put(appUrl, appName);
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

	public String[] getToMailAddrs() {
		return toMailAddrs;
	}

	public void setToMailAddrs(String[] toMailAddrs) {
		this.toMailAddrs = toMailAddrs;
	}
	
	
}
