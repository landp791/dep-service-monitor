package com.dep.monitor.quartz;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dep.monitor.model.AppOwner;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.dep.monitor.service.MonitorService;
import com.google.common.collect.Maps;

@Component
public class OneAppMonitorJob{
	private static final Log logger = LogFactory.getLog(OneAppMonitorJob.class);
	private static final long ONE_HOUR = 1 * 60 * 60 * 1000l;
	
	private static Map<String, Long> map = Maps.newConcurrentMap();
	
	private MonitorService monitorService;
	
	private AppOwnerReadRepository appOwnerReadRepo;
	
	public OneAppMonitorJob() {
		monitorService = (MonitorService)ContextHolder.getBean("monitorService");
		appOwnerReadRepo = (AppOwnerReadRepository)ContextHolder.getBean("appOwnerReadRepository");
	}
	
	@Scheduled(fixedDelay = 10000)
	public void execute() throws JobExecutionException {
		try {
			long now = System.currentTimeMillis();
		    logger.debug("OneAppMonitorJob quartz runs once!!now:" + now);
			List<AppOwner> apps = appOwnerReadRepo.findAll();
			for (AppOwner app : apps) {
				if (!haveMonitoredInOneHour(app)) {
					monitorService.monitorOneApp(app.getAppUrl());
				}
			}
		} catch (Exception e) {
			logger.error("Execute OneAppMonitorJob fail.", e);
		} 
	}
	
	private boolean haveMonitoredInOneHour(AppOwner app) {
		Long lastMonitorTime = map.get(app.getAppUrl());
		long now = System.currentTimeMillis();
		if (lastMonitorTime == null || (now - lastMonitorTime) > ONE_HOUR) {
			map.put(app.getAppUrl(), now);
			return false;
		}
		return true;
	}
}
