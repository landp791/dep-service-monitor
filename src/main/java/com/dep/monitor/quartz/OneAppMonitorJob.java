package com.dep.monitor.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dep.monitor.model.AppOwner;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.dep.monitor.service.MonitorService;

public class OneAppMonitorJob extends QuartzJobBean{
	private static final Log logger = LogFactory.getLog(OneAppMonitorJob.class);
	
	private MonitorService monitorService;
	
	private AppOwnerReadRepository appOwnerReadRepo;
	
	public OneAppMonitorJob() {
		monitorService = (MonitorService)ContextHolder.getBean("monitorService");
		appOwnerReadRepo = (AppOwnerReadRepository)ContextHolder.getBean("appOwnerReadRepository");
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		try {
		    logger.debug("OneAppMonitorJob quartz runs begin!!now:" + System.currentTimeMillis());
			List<AppOwner> apps = appOwnerReadRepo.findAll();
            for (AppOwner app : apps) {
                monitorService.monitorOneApp(app.getAppUrl());
            }
			logger.debug("OneAppMonitorJob quartz runs finish!!now:" + System.currentTimeMillis());
		} catch (Exception e) {
			logger.error("Execute OneAppMonitorJob fail.", e);
		} 
	}
}
