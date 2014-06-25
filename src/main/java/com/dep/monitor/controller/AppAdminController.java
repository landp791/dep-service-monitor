package com.dep.monitor.controller;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dep.monitor.model.AllAppOwners;
import com.dep.monitor.model.App;
import com.dep.monitor.model.AppOwner;
import com.dep.monitor.model.Owner;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.dep.monitor.repo.read.AppReadRepository;
import com.dep.monitor.repo.read.OwnerReadRepository;
import com.dep.monitor.service.MonitorService;

@Controller
public class AppAdminController {
	private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	private MonitorService monitorService;
	
	@Autowired
	private AppReadRepository appRepo;
	
	@Autowired
	private AppOwnerReadRepository appOwnerRepo;
	
	@Autowired
	private OwnerReadRepository ownerRepo;
	
	@RequestMapping(value="/service/add")
	public void addService(@RequestParam("appUrl") String url,
			@RequestParam("appName") String appName,
			@RequestParam("owners") String ownersStr) {
		logger.debug("add service is invoked!");
		String[] owners = StringUtils.split(ownersStr, ",");
		if (ArrayUtils.isEmpty(owners) || StringUtils.isEmpty(url)) {
			logger.warn("Sevice added is not valid![" + url +"|" + ownersStr +"]");
			return;
		}
		
		monitorService.saveAppMonitored(url, appName, owners);
	}
	
	@RequestMapping(value="/all/query")
	public String queryAllApp() {
		List<App> apps = appRepo.findAll();
		List<AppOwner> appOwners = appOwnerRepo.findAll();
		List<Owner> owners = ownerRepo.findAll();
//		AllAppOwners all = new AllAppOwners(apps, appOwners, owners);
//		return all.buildJson();
		return "";
	}
	
	@RequestMapping(value="/service/update")
	public String updateService(@RequestParam("appUrl")String url
			) {
		return null;
	}
	
	@RequestMapping(value="/service/delete")
	public String deleteService(@RequestParam("appUrl")String url
			) {
		return null;
	}	
	
	
}
