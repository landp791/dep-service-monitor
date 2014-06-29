package com.dep.monitor.mail;

import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_BAD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_GOOD;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.MailInfo;
import com.google.common.collect.Maps;
import com.sina.sae.mail.SaeMail;

@Service
@Scope("prototype")
public class SpecifiedServiceMailSender implements MailSender{
	private static final Log logger = LogFactory.getLog(SpecifiedServiceMailSender.class);
	
	private final String GOOD_NEWS_TEMPLATE = "good_news_mail.vm";
	private final String BAD_NEWS_TEMPLATE = "bad_news_mail.vm";
	
	@Autowired
	private SAEMailHelper mailHelper;
	
    @Resource(name = "velocityConfigurer")
    private VelocityConfigurer velocityConfigurer;
	
	private String templateFile;
	
	private String subject;
	
	private String content;
	
	@Override
	public void send(MailInfo mailInfo) throws Exception {
		SaeMail mail = mailHelper.newSaeMailInstance(mailInfo.getToMailAddrs());
		setMailInfo(mailInfo);
		mailHelper.doSend(mail, new String(subject.getBytes(), "UTF-8"), content);
		logger.debug("send bad news mail finish.");
	}
	
	private void setMailInfo(MailInfo mailInfo) {
		if (MAIL_TYPE_SPECIFIED_GOOD.equals(mailInfo.getType())) {
			templateFile = GOOD_NEWS_TEMPLATE;
			subject = "[部门服务监控]服务已经正常工作";
			
			Map<String, Object> model = Maps.newHashMap();
			model.put("url", mailInfo.getGoodUrls().get(0));
			model.put("appNames", mailInfo.getAppNames());
			setContent(model);
			
		} else if (MAIL_TYPE_SPECIFIED_BAD.equals(mailInfo.getType())){
			templateFile = BAD_NEWS_TEMPLATE;
			subject = "[部门服务监控]您负责的服务没有正常工作！请处理";
			
			Map<String, Object> model = Maps.newHashMap();
			model.put("url", mailInfo.getBadUrls().get(0));
			model.put("appNames", mailInfo.getAppNames());
			setContent(model);
		}
	}
	
	private void setContent(Map<String, Object> model) {
        content = VelocityEngineUtils.mergeTemplateIntoString(        		
                velocityConfigurer.getVelocityEngine(), templateFile,
                MAIL_ENCODING, model);
	}

}