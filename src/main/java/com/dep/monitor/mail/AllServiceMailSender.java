package com.dep.monitor.mail;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.MailInfo;
import com.google.common.collect.Maps;
import com.sina.sae.mail.SaeMail;

@Service
public class AllServiceMailSender implements MailSender{
	private static final Log logger = LogFactory.getLog(AllServiceMailSender.class);
	
	private static final String ALL_NEWS_TEMPLATE = "all_news_mail.vm";
	private static final String subject = "[部门服务监控]部门服务监控统计";
	
    @Resource(name = "velocityConfigurer")
    private VelocityConfigurer velocityConfigurer;
	
	@Autowired
	private SAEMailHelper mailHelper;
	
	@Override
	public void send(MailInfo mailInfo) throws Exception {
		SaeMail mail = mailHelper.newSaeMailInstance(mailInfo.getToMailAddrs());

		mailHelper.doSend(mail, new String(subject.getBytes(), "UTF-8"), getContent(mailInfo));
		logger.debug("send all service news mail finish.");
	}
	
	public String getContent(MailInfo mailInfo) {
		Map<String, Object> model = Maps.newHashMap();
		model.put("badUrls", mailInfo.getBadUrls());
		model.put("goodUrls", mailInfo.getGoodUrls());
		model.put("appNames", mailInfo.getAppNames());
		
        return VelocityEngineUtils.mergeTemplateIntoString(        		
                velocityConfigurer.getVelocityEngine(), ALL_NEWS_TEMPLATE,
                MAIL_ENCODING, model);
	}
}