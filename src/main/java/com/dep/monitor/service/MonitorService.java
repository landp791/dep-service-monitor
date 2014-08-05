package com.dep.monitor.service;

import static com.dep.monitor.util.HttpClientHelper.isOK;
import static com.dep.monitor.util.MonitorConstants.APP_STATUS_BAD;
import static com.dep.monitor.util.MonitorConstants.APP_STATUS_GOOD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_ALL;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_BAD;
import static com.dep.monitor.util.MonitorConstants.MAIL_TYPE_SPECIFIED_GOOD;
import static java.lang.String.format;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dep.monitor.controller.MonitorController;
import com.dep.monitor.model.AppOwner;
import com.dep.monitor.model.MonitorInfo;
import com.dep.monitor.repo.read.AppOwnerReadRepository;
import com.google.common.collect.Maps;

@Service
public class MonitorService {
	private static final Log logger = LogFactory.getLog(MonitorController.class);
	
    @Value("${dep_mail}")
	private String DEP_MAIL_ADDR;
	
	@Autowired
	private MailService mailService; 
	
	@Autowired
	private AppOwnerReadRepository appOwnerReadRepo;
	
	private HttpClient httpClient;
	
	@PostConstruct
	public void init() {
	    ClientConnectionManager connManager = new PoolingClientConnectionManager();
	    httpClient = new DefaultHttpClient(connManager);
	}

	@PreDestroy
	public void onDestroy() {
		if (httpClient != null){
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public boolean monitor(String url) {
		try {
			HttpHead head = new HttpHead(url);
			HttpResponse resp = httpClient.execute(head);
			boolean result = isOK(resp);
			logger.info("Monitoring url:" + url + ". Result:" + result);
			return result;
		} catch (ClientProtocolException e) {
			logger.error("Client protocal is not valid![" + url + "]", e);
		} catch (IOException e) {
			logger.error("Something is wrong![" + url + "]", e);
		}
		logger.error("Monitoring url:" + url + " occurs Exception.");
		return false;
	}

	private void monitorAndMarkDownResult(AppOwner... apps) {
		for (AppOwner app : apps) {
		    logger.info(format("Monitor appName:%s|appUrl:%s", app.getAppName(), app.getAppUrl()));
			if (monitor(app.getAppUrl())) {
				app.setStatus(APP_STATUS_GOOD);
			} else {
				app.setStatus(APP_STATUS_BAD);
			}
		}
	}

	private MonitorInfo prepareMailInfo(String flag, AppOwner... apps) {
	    if (apps == null) {
	        return null;
	    }
		if ("one".equals(flag)) {
			return new SpecifiedServiceMailInfoBuilder(apps[0]).build();
		} else {
			return new AllServiceMailInfoBuilder(apps).build();
		}
	}
	
	private class SpecifiedServiceMailInfoBuilder{
		private AppOwner app;
		private MonitorInfo mailInfo;
		
		public SpecifiedServiceMailInfoBuilder(AppOwner app) {
			this.app = app;
			mailInfo = new MonitorInfo();
		}
		
		public MonitorInfo build() {
			// set type
			setType();
			
			// set Url
			setUrl();
			
			// set tomail
			setToMail();
			
			// set app name
			addAppName();
			
			return mailInfo;
		}
		private void setType() {
			if (APP_STATUS_GOOD == app.getStatus()) {
				mailInfo.setType(MAIL_TYPE_SPECIFIED_GOOD);
			} else {
				mailInfo.setType(MAIL_TYPE_SPECIFIED_BAD);
			}
		}
		
		private void setUrl(){
			if (APP_STATUS_GOOD == app.getStatus()) {
				mailInfo.addGoodUrl(this.app.getAppUrl());
			} else {
				mailInfo.addBadUrl(this.app.getAppUrl());
			}
		}
		
		private void setToMail(){
			String ownerStr = app.getOwner();
			String[] tomails = ownerStr.split(",");
			mailInfo.setToMailAddrs(tomails);
		}
		
		private void addAppName() {
			mailInfo.addAppName(app.getAppUrl(),app.getAppName());
		}
	}

	private class AllServiceMailInfoBuilder{
		private AppOwner[] apps;
		private MonitorInfo mailInfo;
		
		public AllServiceMailInfoBuilder(AppOwner... apps) {
			this.apps = apps;
			mailInfo = new MonitorInfo();
		}
		
		public MonitorInfo build() {
			setType();
			setUrl();
			setToMail();
			setAppNames();
			return mailInfo;
		}
		
		private void setType() {
			mailInfo.setType(MAIL_TYPE_ALL);
		}
		
		private void setAppNames(){
			for (AppOwner app : apps) {
				mailInfo.addAppName(app.getAppUrl(), app.getAppName());
			}
		}
		
		private void setUrl() {
			for (AppOwner app : apps) {
				if (APP_STATUS_GOOD == app.getStatus()) {
					mailInfo.addGoodUrl(app.getAppUrl());
				} else {
					mailInfo.addBadUrl(app.getAppUrl());
				}
			}
		}
		
		private void setToMail() {
			mailInfo.setToMailAddrs(new String[]{DEP_MAIL_ADDR});
		}
	}

	public void monitorAllApps() throws Exception {
		List<AppOwner> apps = appOwnerReadRepo.findAll();
		if (CollectionUtils.isEmpty(apps)) {
			logger.warn("No service configured for monitoring!");
			return;
		}
		
		AppOwner[] array = apps.toArray(new AppOwner[apps.size()]);
		monitorAndMarkDownResult(array);
		MonitorInfo mailInfo = prepareMailInfo("all",array);
		mailService.sendMail(mailInfo);
	}
	
	private static Map<String, Integer> lastStates = Maps.newConcurrentMap(); 
	public void monitorOneApp(String url) throws Exception {
		AppOwner appOwner = appOwnerReadRepo.findByAppUrl(url);
		if (appOwner == null) {
			logger.warn("No config for the url!["+ url +"]");
			return;
		}
		
		monitorAndMarkDownResult(appOwner);
        if (isNeedToSendMail(appOwner)){
            lastStates.put(appOwner.getAppUrl(), appOwner.getStatus());
            MonitorInfo mailInfo = prepareMailInfo("one", appOwner);
            mailService.sendMail(mailInfo);
        }
	}

    private boolean isNeedToSendMail(AppOwner appOwner) {
        if (isOKNow(appOwner) && statesNotChanged(appOwner)) {
            logger.debug("App[" + appOwner.getAppUrl() + "] is always ok, not need to send mail.");
            return false;
        } else if (!isOKNow(appOwner) && statesNotChanged(appOwner) && haveMonitoredInTwoHour(appOwner)) {
            logger.info("App[" + appOwner.getAppUrl() + "] is bad and in 2 hours, not need to send mail.");
            return false;
        } // else send mail
        return true;
    }
	
	private boolean isOKNow(AppOwner appOwner) {
	    return APP_STATUS_GOOD == appOwner.getStatus();
	}
	
    private boolean statesNotChanged(AppOwner appOwner) {
        return lastStates.get(appOwner.getAppUrl()) != null &&
                lastStates.get(appOwner.getAppUrl()) == appOwner.getStatus();
    }
    
    private static final long TWO_HOUR = 2 * 60 * 60 * 1000l;
    private static Map<String, Long> map = Maps.newConcurrentMap();
    private boolean haveMonitoredInTwoHour(AppOwner app) {
        Long lastMonitorTime = map.get(app.getAppUrl());
        long now = System.currentTimeMillis();
        map.put(app.getAppUrl(), now);
        return lastMonitorTime != null && (now - lastMonitorTime) < TWO_HOUR;
    }
    
    public static void main(String[] args) {
        Integer statest = lastStates.get("aaaa");
        System.out.println(statest);
    }
}
