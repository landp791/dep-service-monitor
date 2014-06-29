package com.dep.monitor.util;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HeadRequestTest {
	static HttpClient httpClient;
	String url = "http://www.baiduaa.com";
	
	@BeforeClass
	public static void setUp() {
		httpClient = new DefaultHttpClient();
		
	}
	
	@AfterClass
	public static void onDestroy() {
		if (httpClient != null){
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void should_OK_when_run_multi_times() {
		int errorCount = 0;
		int rightCount = 0;
		
		HttpClient httpClient = null;
		for (int i = 0; i< 100; i++) {
			try {
				httpClient = new DefaultHttpClient();
				HttpHead head = new HttpHead(url);
				HttpResponse resp = httpClient.execute(head);
				if (resp.getStatusLine().getStatusCode() == 200) {
					rightCount ++;
				} else {
					errorCount ++;
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally{
				if (httpClient != null) {
					httpClient.getConnectionManager().shutdown();
				}
			}

		}
		
		System.out.println("Shit times:" + errorCount);
		System.out.println("good times:" + rightCount);
	}
}
