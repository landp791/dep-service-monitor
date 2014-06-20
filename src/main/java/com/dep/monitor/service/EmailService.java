package com.dep.monitor.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.ServiceOwner;
import com.google.common.collect.Maps;
import com.sina.sae.mail.SaeMail;

@Service
public class EmailService {
	private static final Log logger = LogFactory.getLog(EmailService.class);
	private static final String MAIL_ENCODING = "UTF-8";
	private static final String GOOD_NEWS_TEMPLATE = "good_news_mail.vm";
	private static final String BAD_NEWS_TEMPLATE = "bad_news_mail.vm";
	
    @Value("${send_mail}")
    private String sendMail;
    
    @Value("${mail_password}")
    private String mailPassword;

    @Value("${smtp_host}")
    private String smtpHost;
    
    @Value("${smtp_port}")
    private int smtpPort;

    @Value("${to_mail}")
    private String toMail;
	
    @Resource(name = "velocityConfigurer")
    private VelocityConfigurer velocityConfigurer;

	public void sendEmails(String url, List<ServiceOwner> appOwners, boolean isOK) {
		if (isOK) {
			sendGoodNews(url, appOwners);
		} else {
			sendBadNews(url, appOwners);
		}
	} 
	
    private void sendGoodNews(String url, List<ServiceOwner> appOwners) {
        SaeMail mail = createSaeMailInstance(appOwners);
        Map<String, Object> model = Maps.newHashMap();
        model.put("url", url);
        
        sendMail("[֪ͨ]����ķ�����������!", mail, model, GOOD_NEWS_TEMPLATE);
        
        logger.debug("send good news mail finish.");
    }
    
	private void sendBadNews(String url, List<ServiceOwner> appOwners){
        SaeMail mail = createSaeMailInstance(appOwners);
        Map<String, Object> model = Maps.newHashMap();
        model.put("url", url);

        sendMail("[֪ͨ]����ķ���û������!", mail, model, BAD_NEWS_TEMPLATE);

        logger.debug("send bad news mail finish.");
	}
	
    private void sendMail(String subject, SaeMail mail, Map<String, Object> model, String temple) {
        mail.setSubject(subject);

        String content = VelocityEngineUtils.mergeTemplateIntoString(        		
                velocityConfigurer.getVelocityEngine(), temple,
                MAIL_ENCODING, model);
        logger.debug(format("mail content:%s", content));

        mail.setContent(content);
        if (!mail.send()) {
        	logger.debug("send mail fail.");
        }
    }
	
    private SaeMail createSaeMailInstance(List<ServiceOwner> appOwners) {
        SaeMail mail = new SaeMail();
        mail.setFrom(sendMail);
        mail.setSmtpUsername(sendMail);
        mail.setSmtpPassword(mailPassword);
        mail.setSmtpHost(smtpHost);
        mail.setSmtpPort(smtpPort);

        mail.setTo(joinToMail(appOwners));
        mail.setContentType("HTML");
        mail.setChartset(MAIL_ENCODING);
        return mail;
    }
    
    private String[] joinToMail(List<ServiceOwner> appOwners) {
    	String[] toMailArray = new String[appOwners.size()];
    	for (ServiceOwner appOwner : appOwners) {
    		ArrayUtils.add(toMailArray, "landongpingpub@163.com");
    	}
    	return toMailArray;
    }
    

}
