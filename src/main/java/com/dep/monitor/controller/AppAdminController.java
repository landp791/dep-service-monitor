package com.dep.monitor.controller;

import static java.lang.String.format;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dep.monitor.model.AppOwner;
import com.dep.monitor.repo.read.AppOwnerReadRepository;

@Controller
public class AppAdminController {
	private static final Log logger = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	private AppOwnerReadRepository appOwnerRepo;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(value="/service/add")
	public void addService(@RequestParam("appUrl") String appUrl,
			@RequestParam("appName") String appName,
			@RequestParam("owner") String owner) {
		logger.debug("add service is invoked!");
		AppOwner appOwner = new AppOwner(appName, appUrl, owner); 
		appOwnerRepo.save(appOwner);
	}
	
	@RequestMapping(value="/all/query")
	@ResponseBody
	public Object queryAllApp() throws JsonGenerationException, JsonMappingException, IOException {
		logger.debug("query all service is invoked!");
		List<AppOwner> apps = appOwnerRepo.findAll();
		logger.debug(format("size of apps: %s", apps.size()));
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
	}
	
	@RequestMapping(value="/service/delete")
	public void deleteService(@RequestParam("id")String id) {
		logger.debug("update service is invoked!");
		Long idLong = Long.valueOf(id);
		appOwnerRepo.delete(idLong);
	}	
	
}
