package com.dep.monitor.mail;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.MailInfo;

public class AllServiceMailSenderTest {
	private VelocityConfigurer velocityConfigurer;
	
	public void setUp() {
		
	}
	
	public MailInfo aMailInfo() {
		MailInfo mailInfo = new MailInfo();
		mailInfo.setAppName("http://www.baidu.com", "baidu");
		mailInfo.setAppName("http://www.sina.com", "sina");
		
		mailInfo.setGoodUrls(Arrays.asList("http://www.baidu.com"));
		mailInfo.setBadUrls(Arrays.asList("http://www.sina.com"));
		
		mailInfo.setToMailAddrs(new String[]{"261047109@qq.com"});
		return mailInfo;
	}

	@Test
	public void should_OK_when_given_normal_data() {
		
	}

}
