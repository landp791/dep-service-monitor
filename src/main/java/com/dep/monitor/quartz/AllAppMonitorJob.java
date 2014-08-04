package com.dep.monitor.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;

import com.dep.monitor.service.MonitorService;

public class AllAppMonitorJob {
	private static final Log logger = LogFactory.getLog(AllAppMonitorJob.class);
	
	private MonitorService monitorService;
	
	public AllAppMonitorJob() {
		monitorService = (MonitorService)ContextHolder.getBean("monitorService");
	}
	
	@Scheduled(cron="0 0 19 ? * MON-FRI")
	protected void execute() throws JobExecutionException {
		try {
		    long now = System.currentTimeMillis();
			logger.debug("AllAppMonitorJob quartz runs once!!now:" + now);
			monitorService.monitorAllApps();
		} catch (Exception e) {
			logger.error("Execute job fail.", e);
		} 
	}
}
