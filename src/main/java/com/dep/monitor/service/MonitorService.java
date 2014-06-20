package com.dep.monitor.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dep.monitor.controller.MonitorController;
import com.dep.monitor.repo.AppOwnerRepository;
import com.dep.monitor.util.HeadRequest;

@Service
public class MonitorService {
	private static final Log logger = LogFactory.getLog(MonitorController.class);

	@Autowired
	HeadRequest head;
	
	@Autowired
	private AppOwnerRepository repository;
	
	public boolean tryToMonitor(String url) {
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

}
