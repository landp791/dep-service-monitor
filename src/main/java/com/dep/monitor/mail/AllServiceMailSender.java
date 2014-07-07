package com.dep.monitor.mail;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.MailInfo;
import com.dep.monitor.model.MailInfoView;
import com.google.common.collect.Maps;

@Service
public class AllServiceMailSender extends MailSenderWithProxy{
	private static final String ALL_NEWS_TEMPLATE = "all_news_mail.vm";
	private static final String subject = "[部门服务监控]部门服务监控统计";
	
    @Resource(name = "velocityConfigurer")
    private VelocityConfigurer velocityConfigurer;
	
	
	private String getContent(MailInfo mailInfo) {
		Map<String, Object> model = Maps.newHashMap();
		model.put("badUrls", mailInfo.getBadUrls());
		model.put("goodUrls", mailInfo.getGoodUrls());
		model.put("appNames", mailInfo.getAppNames());
		
        return VelocityEngineUtils.mergeTemplateIntoString(        		
                velocityConfigurer.getVelocityEngine(), ALL_NEWS_TEMPLATE,
                MAIL_ENCODING, model);
	}

	@Override
	protected MailInfoView refineMailInfoView(MailInfo mailInfo) {
		MailInfoView view = new MailInfoView();
		view.setTo(StringUtils.join(mailInfo.getToMailAddrs(), ","));
		view.setSubject(subject);
		view.setContent(getContent(mailInfo));
		
		return view;
	}
}