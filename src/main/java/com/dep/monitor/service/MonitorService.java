package com.dep.monitor.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import com.dep.monitor.model.ApplicationOwner;

@Service
public class MonitorService {

	public int tryToMonitor(String url) {
		return 0;
	}

	public void sendEmails(String url, List<ApplicationOwner> appOwners, int respCode) {
		// TODO Auto-generated method stub
		
	}

}
