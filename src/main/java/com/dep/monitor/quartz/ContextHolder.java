package com.dep.monitor.quartz;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextHolder implements ApplicationContextAware{
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ContextHolder.context = applicationContext;
	}
	
	public static ApplicationContext getContext() {
		return context;
	}
	
	public static Object getBean(String name) {
		if (context == null) {
			throw new RuntimeException("ContextHolder didn't hold application context.");
		}
		return context.getBean(name);
	}

}
