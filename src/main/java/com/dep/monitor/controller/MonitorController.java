package com.dep.monitor.controller;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dep.monitor.model.App;
import com.dep.monitor.model.MailInfo;
import com.dep.monitor.repo.read.AppReadRepository;
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
	private AppReadRepository appRepository;

	@RequestMapping(value="/service/monitor")
	public void monitorSpecifiedService(@RequestParam("appUrl")String url) {
		logger.warn("Monitoring specified service start." + url);
		App app = appRepository.queryByUrl(url);
		if (app == null) {
			logger.warn("No config for the url!["+ url +"]");
			return;
		}
		
		monitorService.monitorAndMarkResult(app);
		MailInfo mailInfo = monitorService.prepareMailInfo(app);
		mailService.sendMail(mailInfo);
		logger.debug("Monitoring specified service finish." + url);
	}
	
	@RequestMapping(value="/all/monitor")
	public void monitorAllService(){
		logger.debug("Monitoring all services start!");
		App[] apps = appRepository.queryAllApp();
		if (ArrayUtils.isEmpty(apps)) {
			logger.warn("No service configured for monitoring!");
			return;
		}
		
		monitorService.monitorAndMarkResult(apps);
		MailInfo mailInfo = monitorService.prepareMailInfo(apps);
		mailService.sendMail(mailInfo);
		
		logger.debug("Monitoring all services finish!");
	}
	
}
