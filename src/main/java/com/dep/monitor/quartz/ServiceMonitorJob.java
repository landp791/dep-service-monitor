package com.dep.monitor.quartz;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dep.monitor.model.AppOwner;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.dep.monitor.service.ContextHolder;
import com.dep.monitor.util.HeadRequest;

public class ServiceMonitorJob extends QuartzJobBean{
	private static final Log logger = LogFactory.getLog(ServiceMonitorJob.class);
	
//	@Value("$monitor.all.service.url")
	private String url;
	
	@Autowired	
	private AppOwnerReadRepository appOwnerRepo;
	
	private HeadRequest head;
	
	public ServiceMonitorJob() {
		head = (HeadRequest)ContextHolder.getBean("headRequest");
		appOwnerRepo = (AppOwnerReadRepository)ContextHolder.getBean("appOwnerReadRepository");
		// TODO: not completed!
		url = "http://www.baidu.com";
	}

	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		try {
			logger.info("aaaaaaaaaaaa quartz runs once!!");
			
			
			List<AppOwner> apps = appOwnerRepo.findAll();
			
			
			if (head.withUrl(url).isOK()) {
				logger.info("Request all service monitoring successrully.");
			}
		} catch (Exception e) {
			// once error occurs, don't stop the quartz.
			logger.error("Execute job fail.", e);
		} 
	}

}
