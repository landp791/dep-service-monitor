package com.dep.monitor.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.dep.monitor.controller.MonitorController;
import com.dep.monitor.model.App;
import com.dep.monitor.model.MailInfo;
import com.dep.monitor.repo.AppOwnerRepository;
import com.dep.monitor.util.HeadRequest;

import static com.dep.monitor.util.MonitorConstants.APP_STATUS_BAD;
import static com.dep.monitor.util.MonitorConstants.APP_STATUS_GOOD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_ALL;

@org.springframework.stereotype.Service
public class MonitorService {
	private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	private static final String DEP_MAIL_ADDR = "";

	@Autowired
	private HeadRequest head;
	
	@Autowired
	private MailService mailService; 
	
	@Autowired
	private AppOwnerRepository repository;
	
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

	public void monitorAndMarkResult(App... apps) {
		for (App app : apps) {
			if (monitor(app.getAppUrl())) {
				app.setStatus(APP_STATUS_GOOD);
			} else {
				app.setStatus(APP_STATUS_BAD);
			}
		}
	}

	public MailInfo prepareMailInfo(App... apps) {
		if (apps != null && apps.length == 1) {
			SpecifiedServiceMailInfoBuilder builder = new SpecifiedServiceMailInfoBuilder(apps[0]);
			return builder.build();
		} else {
			AllServiceMailInfoBuilder builder = new AllServiceMailInfoBuilder(apps);
			return builder.build();
		}
	}
	
	private class SpecifiedServiceMailInfoBuilder{
		private App app;
		private MailInfo mailInfo;
		
		public SpecifiedServiceMailInfoBuilder(App app) {
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
			
			return mailInfo;
		}
		private void setType() {
			mailInfo.setType(MAIL_TYPE_SPECIFIED);
		}
		
		private void setUrl(){
			if (APP_STATUS_GOOD == app.getStatus()) {
				mailInfo.addGoodUrl(this.app.getAppUrl());
			} else {
				mailInfo.addBadUrl(this.app.getAppUrl());
			}
		}
		
		private void setToMail(){
			List<String> tomails = repository.queryTomailsByAppId(app.getAppId());
			mailInfo.setToMailAddrs(tomails);
		}
	}

	private class AllServiceMailInfoBuilder{
		private App[] apps;
		private MailInfo mailInfo;
		
		public AllServiceMailInfoBuilder(App... apps) {
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
			for (App app : apps) {
				if (APP_STATUS_GOOD == app.getStatus()) {
					mailInfo.addGoodUrl(app.getAppUrl());
				} else {
					mailInfo.addBadUrl(app.getAppUrl());
				}
			}
		}
		
		private void setToMail() {
			mailInfo.setToMailAddrs(Arrays.asList(DEP_MAIL_ADDR));
		}
	}	
	
}
