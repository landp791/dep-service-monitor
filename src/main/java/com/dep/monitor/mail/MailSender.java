package com.dep.monitor.mail;

import com.dep.monitor.model.MailInfo;

public interface MailSender {
	static String MAIL_ENCODING = "UTF-8";

	void send(MailInfo mailInfo) throws Exception;
	
}
