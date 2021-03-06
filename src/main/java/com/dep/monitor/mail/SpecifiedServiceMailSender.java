package com.dep.monitor.mail;

import static java.lang.String.format;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_BAD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_GOOD;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.dep.monitor.model.MonitorInfo;
import com.dep.monitor.model.MailInfo;
import com.google.common.collect.Maps;

/**
 * Send out forward-mail request.
 * @author LANDONGPING791
 *
 */
@Service
public class SpecifiedServiceMailSender extends MailSenderWithProxy{
    private final String GOOD_NEWS_TEMPLATE = "good_news_mail.vm";
    private final String BAD_NEWS_TEMPLATE = "bad_news_mail.vm";
    
    @Resource(name = "velocityConfigurer")
    private VelocityConfigurer velocityConfigurer;
    
    private String templateFile;
    
    private String subject;
    
    private String content;
    
    private void parseMailInfo(MonitorInfo mailInfo){
        if (MAIL_TYPE_SPECIFIED_GOOD.equals(mailInfo.getType())) {
            parseWhenServiceGood(mailInfo);
        } else if (MAIL_TYPE_SPECIFIED_BAD.equals(mailInfo.getType())){
            parseWhenServiceBad(mailInfo);
        } else {
            throw new RuntimeException(format("Not Supported mail Type.[%s]", mailInfo.getType()));
        }
    }
    
    private void parseWhenServiceGood(MonitorInfo mailInfo) {
        templateFile = GOOD_NEWS_TEMPLATE;
        subject = "[部门服务监控]服务正常工作";
        
        Map<String, Object> model = Maps.newHashMap();
        model.put("url", mailInfo.getGoodUrls().get(0));
        model.put("appNames", mailInfo.getAppNames());
        content = mailContent(model);
    }

    private void parseWhenServiceBad(MonitorInfo mailInfo) {
        templateFile = BAD_NEWS_TEMPLATE;
        subject = "[部门服务监控]您负责的服务没有正常工作！请处理";
        
        Map<String, Object> model = Maps.newHashMap();
        model.put("url", mailInfo.getBadUrls().get(0));
        model.put("appNames", mailInfo.getAppNames());
        content = mailContent(model);
    }

    private String mailContent(Map<String, Object> model) {
        return VelocityEngineUtils.mergeTemplateIntoString(             
                velocityConfigurer.getVelocityEngine(), templateFile,
                MAIL_ENCODING, model);
    }

    @Override
    protected MailInfo refineMailInfoView(MonitorInfo mailInfo) {
        parseMailInfo(mailInfo);
        
        MailInfo view = new MailInfo();
        view.setTo(StringUtils.join(mailInfo.getToMailAddrs(), ","));
        view.setSubject(subject);
        view.setContent(content);
        
        return view;
    }

}