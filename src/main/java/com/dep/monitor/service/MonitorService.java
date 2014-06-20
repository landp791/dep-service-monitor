package com.dep.monitor.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.dep.monitor.controller.MonitorController;
import com.dep.monitor.model.App;
import com.dep.monitor.model.MailInfo;
import com.dep.monitor.repo.AppOwnerRepository;
import com.dep.monitor.util.HeadRequest;
import com.mchange.v2.util.CollectionUtils;

import static com.dep.monitor.util.MonitorConstants.APP_STATUS_BAD;
import static com.dep.monitor.util.MonitorConstants.APP_STATUS_GOOD;

@org.springframework.stereotype.Service
public class MonitorService {
	private static final Log logger = LogFactory.getLog(MonitorController.class);

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

	public MailInfo translateToMailInfo(App... apps) {
		
		return null;
	}

}
