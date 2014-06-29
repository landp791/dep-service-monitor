package com.dep.monitor.mail;

import static java.lang.String.format;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sina.sae.mail.SaeMail;

@Component
public class SAEMailHelper {
	private static final Log logger = LogFactory.getLog(SAEMailHelper.class);
	
	private static final String MAIL_ENCODING = "UTF-8";
	
    @Value("${send_mail_from}")
    private String sendMail;

    @Value("${mail_password}")
    private String mailPassword;

    @Value("${smtp_host}")
    private String smtpHost;
    
    @Value("${smtp_port}")
    private int smtpPort;

	public SaeMail newSaeMailInstance(String... mailTo) {
        SaeMail mail = new SaeMail();
        mail.setFrom(sendMail);
        mail.setSmtpUsername(sendMail);
        mail.setSmtpPassword(mailPassword);
        mail.setSmtpHost(smtpHost);
        mail.setSmtpPort(smtpPort);

        mail.setTo(mailTo);
        mail.setContentType("HTML");
        mail.setChartset(MAIL_ENCODING);
        return mail;
    }
	
    public void doSend(SaeMail mail, String subject, String content) {
        mail.setSubject(subject);
        logger.debug(format("mail detail:[subject:%s][content:%s]", subject, content));

        mail.setContent(content);
        if (!mail.send()) {
        	logger.debug("send mail fail.");
        }
    }
	
}
