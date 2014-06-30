package com.dep.monitor.quartz;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dep.monitor.model.AppOwner;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.dep.monitor.service.MonitorService;
import com.google.common.collect.Maps;

public class OneAppMonitorJob extends QuartzJobBean{
	private static final Log logger = LogFactory.getLog(OneAppMonitorJob.class);
	private static final long ONE_HOUR = 1 * 60 * 60 * 1000l;
	
	private static Map<String, Long> map = Maps.newConcurrentMap();
	
	private MonitorService monitorService;
	
	private AppOwnerReadRepository appOwnerReadRepo;
	
	public OneAppMonitorJob() {
		monitorService = (MonitorService)ContextHolder.getBean("monitorService");
		appOwnerReadRepo = (AppOwnerReadRepository)ContextHolder.getBean("appOwnerReadRepository");
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		try {
			logger.debug("OneAppMonitorJob quartz runs once!!");
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
