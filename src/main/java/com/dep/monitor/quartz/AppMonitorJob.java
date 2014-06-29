package com.dep.monitor.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dep.monitor.service.MonitorService;

public class AppMonitorJob extends QuartzJobBean{
	private static final Log logger = LogFactory.getLog(AppMonitorJob.class);
	
	private MonitorService monitorService;
	
	public AppMonitorJob() {
		monitorService = (MonitorService)ContextHolder.getBean("monitorService");
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		try {
			logger.debug("AppMonitorJob quartz runs once!!");
			monitorService.monitorAllApps();
		} catch (Exception e) {
			logger.error("Execute job fail.", e);
		} 
	}
}
