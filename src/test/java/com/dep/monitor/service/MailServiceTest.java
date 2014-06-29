package com.dep.monitor.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dep.monitor.mail.AllServiceMailSender;
import com.dep.monitor.mail.SpecifiedServiceMailSender;
import com.sina.sae.mail.SaeMail;

public class MailServiceTest {
	private String sendMail = "landongpingpub@163.com";
	private String mailPassword = "LDP163pwd";
	private String smtpHost = "smtp.163.com";
	private int smtpPort = 25;
	private String MAIL_ENCODING = "UTF-8";
	private String toMail = "261047109@qq.com";
	
	MailService mailService = new MailService(){

		@Override
		protected SpecifiedServiceMailSender getSpecifiedServiceMailSender() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected AllServiceMailSender getAllServiceMailSender() {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
//	@Test
	public void should_OK_when_send_to_one() {
		SaeMail mail = aSaeMailInstance(toMail);
		boolean result = doSend(mail, "Just a test!", "About test");
		if (!result) {
			fail("send mail failed!");
		}
	}
	
	private boolean doSend(SaeMail mail, String content, String subject) {
		mail.setSubject(subject);
        mail.setContent(content);
        return mail.send();
	}

    private SaeMail aSaeMailInstance(String... addrs) {
        SaeMail mail = new SaeMail();
        mail.setFrom(sendMail);
        mail.setSmtpUsername(sendMail);
        mail.setSmtpPassword(mailPassword);
        mail.setSmtpHost(smtpHost);
        mail.setSmtpPort(smtpPort);

        mail.setTo(addrs);
        mail.setContentType("HTML");
        mail.setChartset(MAIL_ENCODING);
        return mail;
    }
    
    @Test
    public void should_copy_when_toArray() {
    	List<String> list = new ArrayList<String>();
    	list.add("aaaa");
    	list.add("bbbb");
    	list.add("cccc");
    	
    	String[] strs = list.toArray(new String[3]);
    	for (String str : strs) {
    		System.out.println(str);
    	}
    	
    	
    }
}
