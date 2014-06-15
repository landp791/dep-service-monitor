package com.dep.monitor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dep.monitor.service.MonitorService;

@Controller
public class MonitorController {
    private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	private MonitorService monitorService;

	@RequestMapping(value="/app/monitor")
	public void monitorSpecifiedService(@RequestParam("serviceUrl")String serviceUrl) {
		
	}
	
	@RequestMapping(value="/all/monitor")
	public void monitorAllService(){
		logger.info("MonitorAllService is invoked!");
	}
	
	@RequestMapping(value="/app/add")
	public void addService() {
		logger.info("AddService is invoked!");
	}
}
