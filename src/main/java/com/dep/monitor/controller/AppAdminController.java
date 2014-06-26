package com.dep.monitor.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dep.monitor.model.AppOwner;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.dep.monitor.util.StringUtil;

@Controller
public class AppAdminController {
	private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	private AppOwnerReadRepository appOwnerRepo;
	
	@RequestMapping(value="/service/add")
	public void addService(@RequestParam("appUrl") String appUrl,
			@RequestParam("appName") String appName,
			@RequestParam("owner") String owner) {
		logger.debug("add service is invoked!");
		AppOwner appOwner = new AppOwner(appName, appUrl, owner); 
		appOwnerRepo.save(appOwner);
	}
	
	@RequestMapping(value="/all/query")
	public String queryAllApp() {
		logger.debug("query all service is invoked!");
		List<AppOwner> apps = appOwnerRepo.findAll();
		return StringUtil.toJson(apps);
	}
	
	@RequestMapping(value="/service/update")
	public String updateService(@RequestParam("id")String id,
			@RequestParam("appName")String appName,
			@RequestParam("appUrl")String appUrl,
			@RequestParam("owner")String owner) {
		logger.debug("update service is invoked!");
		Long idLong = Long.valueOf(id);
		appOwnerRepo.findOne(idLong);
		AppOwner appOwnerDto = new AppOwner(appName, appUrl, owner); 
		appOwnerRepo.save(appOwnerDto);
		return null;
	}
	
	@RequestMapping(value="/service/delete")
	public String deleteService(@RequestParam("id")String id) {
		logger.debug("update service is invoked!");
		Long idLong = Long.valueOf(id);
		appOwnerRepo.delete(idLong);
		return null;
	}	
	
}
