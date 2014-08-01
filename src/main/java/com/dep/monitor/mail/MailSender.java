package com.dep.monitor.mail;

import com.dep.monitor.model.MonitorInfo;

public interface MailSender {
	static String MAIL_ENCODING = "UTF-8";

	void send(MonitorInfo mailInfo) throws Exception;
	
}
