package com.dep.monitor.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") 
public class HeadRequest {
	HeadRequest request;
	private HttpClient httpClient;
	private HttpHead head;
	
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
	
	public HeadRequest withUrl(String url) throws URISyntaxException {
		head.setURI(new URI(url));
		return this;
	}
	
	public HeadRequest withParameter(String key, String value) {
		head.addHeader(key, value);
		return this;
	}
	
	public int getResponseCode() throws ClientProtocolException, IOException {
		HttpResponse response = httpClient.execute(head);
		return response.getStatusLine().getStatusCode();
	}
	
	public boolean isOK(int responseCode) {
		return responseCode == 200;
	}
	
}
