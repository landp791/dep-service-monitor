package com.dep.monitor.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

@Component
public class HttpRequest {
	private HttpClient httpClient;
	
	@PostConstruct
	public void onInit() {
		httpClient = new DefaultHttpClient();
	}
	
	@PreDestroy
	public void onDestroy() {
		if (httpClient != null){
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public HttpRequest aRequest(String url, String method) {
		
		return new HttpRequest();
	}
	
	public void withParameter(String key, String value) {
		
	}
	
	public void withBody(String key, String value) {
		
	}	
	public HttpResponse getResponse() {
		return null;
	}
}
