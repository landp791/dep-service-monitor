package com.dep.monitor.service;

import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_BAD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_GOOD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dep.monitor.mail.AllServiceMailSender;
import com.dep.monitor.mail.SpecifiedServiceMailSender;
import com.dep.monitor.model.MailInfo;

@Service
public class MailService {
	@Autowired
	private AllServiceMailSender allServiceMailSender;
	
	@Autowired
	private SpecifiedServiceMailSender specifiedServiceMailSender;

    /**
     * Anyway, only one mail would be sent.
     * @param mailInfo
     * @throws Exception 
     */
	public void sendMail(MailInfo mailInfo) throws Exception {
		if (isSpecifiedAppMonitor(mailInfo.getType())) {
			specifiedServiceMailSender.send(mailInfo);
		} else {
			allServiceMailSender.send(mailInfo);
		}
	}
	
	private boolean isSpecifiedAppMonitor(String type) {
		return MAIL_TYPE_SPECIFIED_GOOD.equals(type)
				|| MAIL_TYPE_SPECIFIED_BAD.equals(type);
	}
	
}