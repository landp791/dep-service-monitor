package com.dep.monitor.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.App;
import com.dep.monitor.model.AppOwner;
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

    private void sendGoodNews(String url, List<AppOwner> appOwners) {
        SaeMail mail = createSaeMailInstance(appOwners);
        Map<String, Object> model = Maps.newHashMap();
        model.put("url", url);
        
        sendMail("[Good News]Congratulations!Service work well now.", mail, model, GOOD_NEWS_TEMPLATE);
        
        logger.debug("send good news mail finish.");
    }
    
	private void sendBadNews(String url, List<AppOwner> appOwners){
        SaeMail mail = createSaeMailInstance(appOwners);
        Map<String, Object> model = Maps.newHashMap();
        model.put("url", url);

        sendMail("[部门服务监控]部门服务监控统计", mail, model, BAD_NEWS_TEMPLATE);

        logger.debug("send bad news mail finish.");
	}
    
    public void sendBadServicesMail(Set<App> apps, Set<App> badApps) {
//        SaeMail mail = createSaeMailInstanceForBadServices(badServices);
    	Set<String> allAppsUrls= parseUrls(apps);
    	Set<String> badAppsUrls= parseUrls(badApps);
    	
    	
        SaeMail mail = createSaeMailInstanceForBadServices(null);
        
        Map<String, Object> model = Maps.newHashMap();
//        model.put("url", url);
        

        sendMail("[Bad News]Service goes on strike!Forget to pay salary?", mail, model, BAD_NEWS_TEMPLATE);

        logger.debug("send bad news mail finish.");
    }
    
    private Set<String> parseUrls(Set<App> apps) {
    	return null;
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
    
    private SaeMail createSaeMailInstanceForBadServices(List<AppOwner> appOwners) {
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
	
    private SaeMail createSaeMailInstance(List<AppOwner> appOwners) {
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
    
    private String[] joinToMail(List<AppOwner> appOwners) {
    	String[] toMailArray = new String[appOwners.size()];
    	for (AppOwner appOwner : appOwners) {
    		ArrayUtils.add(toMailArray, "landongpingpub@163.com");
    	}
    	return toMailArray;
    }

    /**
     * 任何时候，只会发送一封邮件
     * @param mailInfo
     */
	public void sendMail(MailInfo mailInfo) {
		
	}
    

}
