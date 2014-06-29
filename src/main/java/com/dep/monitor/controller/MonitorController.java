package com.dep.monitor.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dep.monitor.model.AppOwner;
import com.dep.monitor.model.MailInfo;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.dep.monitor.service.MailService;
import com.dep.monitor.service.MonitorService;

@RestController
public class MonitorController {
    private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	private MonitorService monitorService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private AppOwnerReadRepository appOwnerRepository;

	@RequestMapping(value="/service/monitor")
	public void monitorSpecifiedService(@RequestParam("appUrl")String url) throws Exception {
		logger.warn("Monitoring specified service start." + url);
		AppOwner appOwner = appOwnerRepository.findByAppUrl(url);
		if (appOwner == null) {
			logger.warn("No config for the url!["+ url +"]");
			return;
		}
		
		monitorService.monitorAndMarkResult(appOwner);
		MailInfo mailInfo = monitorService.prepareMailInfo(appOwner);
		mailService.sendMail(mailInfo);
		logger.debug("Monitoring specified service finish." + url);
	}
	
	@RequestMapping(value="/all/monitor")
	public void monitorAllService() throws Exception{
		logger.debug("Monitoring all services start!");
		List<AppOwner> apps = appOwnerRepository.findAll();
		if (CollectionUtils.isEmpty(apps)) {
			logger.warn("No service configured for monitoring!");
			return;
		}
		
		AppOwner[] array =new AppOwner[apps.size()];
		monitorService.monitorAndMarkResult(apps.toArray(array));
		MailInfo mailInfo = monitorService.prepareMailInfo(apps.toArray(array));
		mailService.sendMail(mailInfo);
		logger.debug("Monitoring all services finish!");
	}
	
}
