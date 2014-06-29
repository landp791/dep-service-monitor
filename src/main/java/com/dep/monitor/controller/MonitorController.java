package com.dep.monitor.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dep.monitor.service.MonitorService;

@RestController
public class MonitorController {
    private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	private MonitorService monitorService;
	
	@RequestMapping(value="/service/monitor")
	public void monitorSpecifiedService(@RequestParam("appUrl")String url) throws Exception {
		logger.warn("Monitoring specified service start." + url);
		monitorService.monitorOneApp(url);
		logger.debug("Monitoring specified service finish." + url);
	}
	
	@RequestMapping(value="/all/monitor")
	public void monitorAllService() throws Exception{
		logger.debug("Monitoring all services start!");
		monitorService.monitorAllApps();
		logger.debug("Monitoring all services finish!");
	}
	
}
