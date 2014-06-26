package com.dep.monitor.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dep.monitor.model.AppOwner;
import com.dep.monitor.repo.read.AppOwnerDTOReadRepository;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.dep.monitor.util.StringUtil;

@Controller
public class AppAdminController {
	private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	private AppOwnerReadRepository appOwnerRepo;
	
	@Autowired
	private AppOwnerDTOReadRepository appOwnerDTORepo;
	
	@RequestMapping(value="/service/add")
	public void addService(@RequestParam("appUrl") String appUrl,
			@RequestParam("appName") String appName,
			@RequestParam("owner") String owner) {
		logger.debug("add service is invoked!");
		AppOwner appOwnerDto = new AppOwner(appName, appUrl, owner); 
		appOwnerDTORepo.save(appOwnerDto);
	}
	
	@RequestMapping(value="/all/query")
	public String queryAllApp() {
		List<AppOwner> apps = appOwnerDTORepo.findAll();
		return StringUtil.toJson(apps);
	}
	
	@RequestMapping(value="/service/update")
	public String updateService(@RequestParam("id")String id,
			@RequestParam("appName")String appName,
			@RequestParam("appUrl")String appUrl,
			@RequestParam("owner")String owner) {
		
		Long idLong = Long.valueOf(id);
		appOwnerDTORepo.findOne(idLong);
		AppOwner appOwnerDto = new AppOwner(appName, appUrl, owner); 
		appOwnerDTORepo.save(appOwnerDto);
		return null;
	}
	
	@RequestMapping(value="/service/delete")
	public String deleteService(@RequestParam("id")String id) {
		Long idLong = Long.valueOf(id);
		appOwnerDTORepo.delete(idLong);
		return null;
	}	
	
}
