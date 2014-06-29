package com.dep.monitor.service;

import static java.lang.String.format;
import static com.dep.monitor.util.MonitorConstants.APP_STATUS_BAD;
import static com.dep.monitor.util.MonitorConstants.APP_STATUS_GOOD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_ALL;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_BAD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_GOOD;

import java.io.IOException;
import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dep.monitor.controller.MonitorController;
import com.dep.monitor.model.AppOwner;
import com.dep.monitor.model.MailInfo;
import com.dep.monitor.repo.read.AppOwnerReadRepository;

@Service
public class MonitorService {
	private static final Log logger = LogFactory.getLog(MonitorController.class);
	
    @Value("${dep_mail}")
	private String DEP_MAIL_ADDR;
	
	@Autowired
	private MailService mailService; 
	
	@Autowired
	private AppOwnerReadRepository appOwnerReadRepo;
	
	private HttpClient httpClient = new DefaultHttpClient();

	@PreDestroy
	public void onDestroy() {
		if (httpClient != null){
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public boolean monitor(String url) {
		try {
			HttpHead head = new HttpHead(url);
			HttpResponse resp = httpClient.execute(head);
			int respCode = resp.getStatusLine().getStatusCode();
			logger.debug(format("url:%s;respCode:%d", url, respCode));
			return respCode == HttpStatus.SC_OK;
		} catch (ClientProtocolException e) {
			logger.error("Client protocal is not valid![" + url + "]", e);
		} catch (IOException e) {
			logger.error("Something is wrong![" + url + "]", e);
		}
		return false;
	}

	public void monitorAndMarkResult(AppOwner... apps) {
		for (AppOwner app : apps) {
			if (monitor(app.getAppUrl())) {
				app.setStatus(APP_STATUS_GOOD);
			} else {
				app.setStatus(APP_STATUS_BAD);
			}
		}
	}

	public MailInfo prepareMailInfo(AppOwner... apps) {
		if (apps != null && apps.length == 1) {
			return new SpecifiedServiceMailInfoBuilder(apps[0]).build();
		} else {
			return new AllServiceMailInfoBuilder(apps).build();
		}
	}
	
	private class SpecifiedServiceMailInfoBuilder{
		private AppOwner app;
		private MailInfo mailInfo;
		
		public SpecifiedServiceMailInfoBuilder(AppOwner app) {
			this.app = app;
			mailInfo = new MailInfo();
		}
		
		public MailInfo build() {
			// set type
			setType();
			
			// set Url
			setUrl();
			
			// set tomail
			setToMail();
			
			// set app name
			addAppName();
			
			return mailInfo;
		}
		private void setType() {
			if (APP_STATUS_GOOD == app.getStatus()) {
				mailInfo.setType(MAIL_TYPE_SPECIFIED_GOOD);
			} else {
				mailInfo.setType(MAIL_TYPE_SPECIFIED_BAD);
			}
		}
		
		private void setUrl(){
			if (APP_STATUS_GOOD == app.getStatus()) {
				mailInfo.addGoodUrl(this.app.getAppUrl());
			} else {
				mailInfo.addBadUrl(this.app.getAppUrl());
			}
		}
		
		private void setToMail(){
			String ownerStr = app.getOwner();
			String[] tomails = ownerStr.split(",");
			mailInfo.setToMailAddrs(tomails);
		}
		
		private void addAppName() {
			mailInfo.setAppName(app.getAppUrl(),app.getAppName());
		}
	}

	private class AllServiceMailInfoBuilder{
		private AppOwner[] apps;
		private MailInfo mailInfo;
		
		public AllServiceMailInfoBuilder(AppOwner... apps) {
			this.apps = apps;
			mailInfo = new MailInfo();
		}
		
		public MailInfo build() {
			setType();
			setUrl();
			setToMail();
			setAppNames();
			return mailInfo;
		}
		
		private void setType() {
			mailInfo.setType(MAIL_TYPE_ALL);
		}
		
		private void setAppNames(){
			for (AppOwner app : apps) {
				mailInfo.setAppName(app.getAppUrl(), app.getAppName());
			}
		}
		
		private void setUrl() {
			for (AppOwner app : apps) {
				if (APP_STATUS_GOOD == app.getStatus()) {
					mailInfo.addGoodUrl(app.getAppUrl());
				} else {
					mailInfo.addBadUrl(app.getAppUrl());
				}
			}
		}
		
		private void setToMail() {
			mailInfo.setToMailAddrs(new String[]{DEP_MAIL_ADDR});
		}
	}

	public void monitorAllApps() throws Exception {
		List<AppOwner> apps = appOwnerReadRepo.findAll();
		if (CollectionUtils.isEmpty(apps)) {
			logger.warn("No service configured for monitoring!");
			return;
		}
		
		AppOwner[] array = apps.toArray(new AppOwner[apps.size()]);
		monitorAndMarkResult(array);
		MailInfo mailInfo = prepareMailInfo(array);
		mailService.sendMail(mailInfo);
	}
}
