package com.dep.monitor.controller;

import static java.lang.String.format;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dep.monitor.model.AppOwner;
import com.dep.monitor.repo.read.AppOwnerReadRepository;

@RestController
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
		logger.debug("add service is finished!");
	}
	
	@RequestMapping(value="/all/query")
	public Object queryAllApp() throws JsonGenerationException, JsonMappingException, IOException {
		logger.debug("query all service is invoked!");
		List<AppOwner> apps = appOwnerRepo.findAll();
		if (!CollectionUtils.isEmpty(apps)) {
		    logger.debug(format("query all apps is finished.size of apps: %s", apps.size()));
		} else {
		    logger.debug("query all apps is finished.size of apps: 0");
		}
		return apps;
	}
	
	@RequestMapping(value="/service/update")
	public void updateService(@RequestParam("id")String id,
			@RequestParam("appName")String appName,
			@RequestParam("appUrl")String appUrl,
			@RequestParam("owner")String owner) {
		logger.debug("update service is invoked!");
		Long idLong = Long.valueOf(id);
		logger.debug("update service is invoked!id:" + id);
		AppOwner appOwnerDto = appOwnerRepo.findOne(idLong);
		appOwnerDto.setAppName(appName);
		appOwnerDto.setAppUrl(appUrl);
		appOwnerDto.setOwner(owner);
		appOwnerRepo.save(appOwnerDto);
		logger.debug("update service is finished!");
	}
	
	@RequestMapping(value="/service/delete")
	public void deleteService(@RequestParam("id")String id) {
		logger.debug(format("delete service is invoked!id:%s", id));
		Long idLong = Long.valueOf(id);
		appOwnerRepo.delete(idLong);
		logger.debug(format("delete service is finished!id:%s", id));
	}
	
	@RequestMapping(value="/helloworld")
	public void helloworld(@RequestParam("id")String id) {
		logger.debug("helloworld is ok!id:" + id);
		
	}
	
}
