package com.dep.monitor.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.ApplicationOwner;
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

	public void sendEmails(String url, List<ApplicationOwner> appOwners, boolean isOK) {
		if (isOK) {
			sendGoodNews(url, appOwners);
		} else {
			sendBadNews(url, appOwners);
		}
	} 
	
    private void sendGoodNews(String url, List<ApplicationOwner> appOwners) {
        SaeMail mail = createSaeMailInstance();
        Map<String, Object> model = Maps.newHashMap();
        model.put("service", appOwners);

        sendMail("[通知]您负责的服务已经正常工作!", mail, model, GOOD_NEWS_TEMPLATE);

        logger.debug("send good news mail finish.");
    }
    
	private void sendBadNews(String url, List<ApplicationOwner> appOwners){
        SaeMail mail = createSaeMailInstance();
        Map<String, Object> model = Maps.newHashMap();
        model.put("service", appOwners);

        sendMail("[通知]您负责的服务没有正常工作!", mail, model, BAD_NEWS_TEMPLATE);

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
	

	
    private SaeMail createSaeMailInstance() {
        SaeMail mail = new SaeMail();
        mail.setFrom(sendMail);
        mail.setSmtpUsername(sendMail);
        mail.setSmtpPassword(mailPassword);
        mail.setSmtpHost(smtpHost);
        mail.setSmtpPort(smtpPort);

        mail.setTo(toMail.split(","));
        mail.setContentType("HTML");
        mail.setChartset("UTF-8");
        return mail;
    }
}
