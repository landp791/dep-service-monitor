package com.dep.monitor.quartz;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dep.monitor.util.HeadRequest;

public class ServiceMonitorJob extends QuartzJobBean{
	private static final Log logger = LogFactory.getLog(ServiceMonitorJob.class);
	
	@Value("$monitor.all.service.url")
	private String url;
	
	@Autowired
	HeadRequest head;

	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		try {
			logger.info("aaaaaaaaaaaa quartz runs once!!");
			if (head.withUrl(url).isOK()) {
				logger.info("Request all service monitoring successrully.");
			}
		} catch (Exception e) {
			// once error occurs, don't stop the quartz.
			logger.error("Execute job fail.", e);
		} 
	}

}
