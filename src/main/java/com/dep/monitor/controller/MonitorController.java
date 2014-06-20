package com.dep.monitor.controller;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dep.monitor.model.App;
import com.dep.monitor.model.MailInfo;
import com.dep.monitor.repo.AppOwnerRepository;
import com.dep.monitor.service.MailService;
import com.dep.monitor.service.MonitorService;

@Controller
public class MonitorController {
    private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	private MonitorService monitorService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private AppOwnerRepository repository;

	@RequestMapping(value="/service/monitor")
	public void monitorSpecifiedService(@RequestParam("appUrl")String url) {
		logger.debug("Monitoring specified service start." + url);
		App app = repository.queryByUrl(url);
		if (app == null) {
			logger.warn("No config for the url!["+ url +"]");
			return;
		}
		
		monitorService.monitorAndMarkResult(app);
		MailInfo mailInfo = monitorService.translateToMailInfo(app);
		mailService.sendMail(mailInfo);
		logger.debug("Monitoring specified service finish." + url);
	}
	
	@RequestMapping(value="/all/monitor")
	public void monitorAllService(){
		logger.debug("Monitoring all services start!");
		App[] apps = repository.queryAllApp();
		if (ArrayUtils.isEmpty(apps)) {
			logger.warn("No service configured for monitoring!");
			return;
		}
		
		monitorService.monitorAndMarkResult(apps);
		MailInfo mailInfo = monitorService.translateToMailInfo(apps);
		mailService.sendMail(mailInfo);
		
		logger.debug("Monitoring all services finish!");
	}
	
	@RequestMapping(value="/app/add")
	public void addService() {
		logger.debug("AddService is invoked!");
	}
}
