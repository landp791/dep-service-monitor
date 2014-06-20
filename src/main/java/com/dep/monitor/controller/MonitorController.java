package com.dep.monitor.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dep.monitor.model.ServiceOwner;
import com.dep.monitor.repo.AppOwnerRepository;
import com.dep.monitor.service.EmailService;
import com.dep.monitor.service.MonitorService;

@Controller
public class MonitorController {
    private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	private MonitorService monitorService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AppOwnerRepository repository;

	@RequestMapping(value="/service/monitor")
	public void monitorSpecifiedService2(@RequestParam("serviceUrl")String url) {
		logger.debug("Monitor specified service start." + url);
		List<ServiceOwner> appOwners = repository.queryByUrl(url);
		if (CollectionUtils.isEmpty(appOwners)) {
			logger.warn("No config for the url!["+ url +"]");
			return;
		}
		boolean isOK = monitorService.tryToMonitor(url);
		
		emailService.sendEmails(url, appOwners, isOK);
		logger.debug("Monitor specified service finish." + url);
	}
	
	@RequestMapping(value="/all/monitor")
	public void monitorAllService(){
		logger.debug("MonitorAllService is invoked!");
	}
	
	@RequestMapping(value="/app/add")
	public void addService() {
		logger.debug("AddService is invoked!");
	}
}
