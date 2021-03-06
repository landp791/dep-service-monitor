package com.dep.monitor.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dep.monitor.service.MonitorService;

public class AllAppMonitorJob extends QuartzJobBean{
	private static final Log logger = LogFactory.getLog(AllAppMonitorJob.class);
	
	private MonitorService monitorService;
	
	public AllAppMonitorJob() {
		monitorService = (MonitorService)ContextHolder.getBean("monitorService");
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		try {
			logger.info("AllAppMonitorJob quartz runs begin!!now:" + System.currentTimeMillis());
			monitorService.monitorAllApps();
			logger.info("AllAppMonitorJob quartz runs finish!!now:" + System.currentTimeMillis());
		} catch (Exception e) {
			logger.error("Execute job fail.", e);
		} 
	}
}
