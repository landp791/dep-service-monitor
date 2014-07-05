package com.dep.monitor.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;

import com.dep.monitor.model.MailInfo;
import com.google.common.collect.Maps;

public class AllServiceMailSenderWithProxy implements MailSender{
	private static final Log logger = LogFactory.getLog(AllServiceMailSenderWithProxy.class);
	
	@Value("${proxy}")
	private String proxyUrl;
	
	private String destUrl;

	@Override
	public void send(MailInfo mailInfo) throws Exception {
		HttpPost hp = new HttpPost();
		
		
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient hc = new DefaultHttpClient();
		
//		HttpPost hp = new HttpPost("http://localhost:9898/depmonitor/api/helloworld?id=1111", paras);
//		hp.addHeader("id", "aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//		HttpParams paras = new DefaultedHttpParams("", ""); 
//		
//		hp.setParams(params);
		
		
		

		Map<String, String> paras = Maps.newHashMap();
		paras.put("id", "aaaaaa");

		HttpPost hp = new HttpPost("http://localhost:9898/depmonitor/api/helloworld");
		
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		NameValuePair nv = new BasicNameValuePair("id", "aaaaaaaaa");
		nvps.add(nv);
		hp.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8")); 
		
		HttpResponse hresp = hc.execute(hp);
		System.out.println(hresp.getStatusLine().getStatusCode());
	}
	
	
}