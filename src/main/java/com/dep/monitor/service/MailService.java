package com.dep.monitor.service;

import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_BAD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_GOOD;
import static java.lang.String.format;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.MailInfo;
import com.google.common.collect.Maps;
import com.sina.sae.mail.SaeMail;

@Service
public class MailService {
	private static final Log logger = LogFactory.getLog(MailService.class);
	private static final String MAIL_ENCODING = "UTF-8";
	private static final String GOOD_NEWS_TEMPLATE = "good_news_mail.vm";
	private static final String BAD_NEWS_TEMPLATE = "bad_news_mail.vm";
	private static final String ALL_NEWS_TEMPLATE = "all_news_mail.vm";
	
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

    private SaeMail createSaeMailInstance(String[] addrs) {
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

    /**
     * Anyway, only one mail would be sent.
     * @param mailInfo
     */
	public void sendMail(MailInfo mailInfo) {
		if (MAIL_TYPE_SPECIFIED_GOOD == mailInfo.getType() || MAIL_TYPE_SPECIFIED_BAD == mailInfo.getType()) {
			SpecifiedServiceMailSender sender = new SpecifiedServiceMailSender(mailInfo);
			sender.sendMail();
		} else {
			AllServiceMailSender sender = new AllServiceMailSender(mailInfo);
			sender.sendMail();
		}
	}
	
    private void doSend(String subject, SaeMail mail, Map<String, Object> model, String temple) {
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
    
    private class SpecifiedServiceMailSender{
    	private MailInfo mailInfo;
    	
    	public SpecifiedServiceMailSender(MailInfo mailInfo) {
    		this.mailInfo = mailInfo;
    	}
    	
    	public void sendMail() {
            SaeMail mail = createSaeMailInstance(this.mailInfo.getToMailAddrs());
            Map<String, Object> model = Maps.newHashMap();
            
            String templateFile = null;
            String subject = null;
            if (MAIL_TYPE_SPECIFIED_GOOD.equals(mailInfo.getType())) {
            	templateFile = GOOD_NEWS_TEMPLATE;
            	subject = "[Good News]Congratulations!Service work well now.";
            	model.put("url", mailInfo.getGoodUrls().get(0));
            } else {
            	templateFile = BAD_NEWS_TEMPLATE;
            	subject = "[Bad News]Service goes on strike!Forget to pay salary?";
            	model.put("url", mailInfo.getBadUrls().get(0));
            }

            doSend(subject, mail, model, templateFile);
            logger.debug("send bad news mail finish.");
    	}
    }
    
    private class AllServiceMailSender{
    	private MailInfo mailInfo;
    	
    	public AllServiceMailSender(MailInfo mailInfo) {
    		this.mailInfo = mailInfo;
    	}
    	
    	public void sendMail() {
            SaeMail mail = createSaeMailInstance(this.mailInfo.getToMailAddrs());
            Map<String, Object> model = Maps.newHashMap();
            
            String templateFile = null;
            String subject = null;
			templateFile = ALL_NEWS_TEMPLATE;
			subject = "[部门服务监控]部门服务监控统计";
			model.put("badUrls", mailInfo.getBadUrls());
			model.put("goodUrls", mailInfo.getGoodUrls());

            doSend(subject, mail, model, templateFile);
            logger.debug("send all service news mail finish.");
    	}
    }    
}