package com.dep.monitor.service;

import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_BAD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_GOOD;

import com.dep.monitor.mail.AllServiceMailSender;
import com.dep.monitor.mail.SpecifiedServiceMailSender;
import com.dep.monitor.model.MailInfo;

public abstract class MailService {
	
	protected abstract SpecifiedServiceMailSender getSpecifiedServiceMailSender();
	
	protected abstract AllServiceMailSender getAllServiceMailSender();

    /**
     * Anyway, only one mail would be sent.
     * @param mailInfo
     * @throws Exception 
     */
	public void sendMail(MailInfo mailInfo) throws Exception {
		if (isSpecifiedAppMonitor(mailInfo.getType())) {
			getSpecifiedServiceMailSender().send(mailInfo);
		} else {
			getAllServiceMailSender().send(mailInfo);
		}
	}
	
	private boolean isSpecifiedAppMonitor(String type) {
		return MAIL_TYPE_SPECIFIED_GOOD.equals(type)
				|| MAIL_TYPE_SPECIFIED_BAD.equals(type);
	}
	
}