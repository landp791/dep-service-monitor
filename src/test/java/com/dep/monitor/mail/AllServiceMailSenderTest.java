package com.dep.monitor.mail;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.MonitorInfo;

public class AllServiceMailSenderTest {
	private VelocityConfigurer velocityConfigurer;
	
	public void setUp() {
		
	}
	
	public MonitorInfo aMailInfo() {
		MonitorInfo mailInfo = new MonitorInfo();
		mailInfo.addAppName("http://www.baidu.com", "baidu");
		mailInfo.addAppName("http://www.sina.com", "sina");
		
		mailInfo.setGoodUrls(Arrays.asList("http://www.baidu.com"));
		mailInfo.setBadUrls(Arrays.asList("http://www.sina.com"));
		
		mailInfo.setToMailAddrs(new String[]{"261047109@qq.com"});
		return mailInfo;
	}

	@Test
	public void should_OK_when_given_normal_data() {
		
	}

}
