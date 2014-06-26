package com.dep.monitor.service;

import static com.dep.monitor.util.MonitorConstants.APP_STATUS_BAD;
import static com.dep.monitor.util.MonitorConstants.APP_STATUS_GOOD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_ALL;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_BAD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_GOOD;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.dep.monitor.controller.MonitorController;
import com.dep.monitor.model.AppOwner;
import com.dep.monitor.model.MailInfo;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.dep.monitor.util.HeadRequest;

@org.springframework.stereotype.Service
public class MonitorService {
	private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	private static final String DEP_MAIL_ADDR = "";

	@Autowired
	private HeadRequest head;
	
	@Autowired
	private MailService mailService; 
	
	@Autowired
	private AppOwnerReadRepository appOwnerReadRepo;
	
	public boolean monitor(String url) {
		try {
			return head.withUrl(url).isOK();
		} catch (URISyntaxException e) {
			logger.error("Url is not valid![" + url + "]", e);
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
			SpecifiedServiceMailInfoBuilder builder = new SpecifiedServiceMailInfoBuilder(apps[0]);
			return builder.build();
		} else {
			AllServiceMailInfoBuilder builder = new AllServiceMailInfoBuilder(apps);
			return builder.build();
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
			
			// set Url
			setUrl();
			
			// set tomail
			setToMail();
			
			return mailInfo;
		}
		
		private void setType() {
			mailInfo.setType(MAIL_TYPE_ALL);
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

//	// should in transaction
//	public void saveAppMonitored(String url, String appName, String[] owners) {
//		App app = new App(url, appName);
//		App appSaved = appWriteRepository.save(app);
//		
//		Set<AppOwnerDep> set = new HashSet<AppOwnerDep>();
//		for (String owner: owners) {
//			AppOwnerDep ao = new AppOwnerDep(appSaved.getId(), owner);
//			set.add(ao);
//		}
//		appOwnerWriteRepository.save(set);
//	}	
	
}
