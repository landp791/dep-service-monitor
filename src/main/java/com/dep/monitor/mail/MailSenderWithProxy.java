package com.dep.monitor.mail;

import static java.lang.String.format;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dep.monitor.model.MailInfo;
import com.dep.monitor.model.MailInfoView;
import com.google.common.collect.Lists;

@Component
public abstract class MailSenderWithProxy implements MailSender {
	private static final Log log = LogFactory.getLog(MailSenderWithProxy.class);
	
	@Value("${proxy.host}")
	private String proxyHost;
	
	@Value("${proxy.port}")
	private int proxyPort;

	@Value("${proxy.user.name}")
	private String proxyUserName;

	@Value("${proxy.password}")
	private String proxyPassword;

	@Value("${destination}")
	private String dest;

	private DefaultHttpClient httpClient;

	@PostConstruct
	public void init() {
		httpClient = new DefaultHttpClient();

		// set proxy access
		AuthScope authscope=new AuthScope(proxyHost, proxyPort);
        Credentials credentials=new UsernamePasswordCredentials(proxyUserName, proxyPassword);
        httpClient.getCredentialsProvider().setCredentials(authscope, credentials);
	}

	@PreDestroy
	public void onDestroy() {
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	@Override
	public void send(MailInfo mailInfo) throws Exception {
		MailInfoView mailInfoView = refineMailInfoView(mailInfo);
		
		List<NameValuePair> nvps = Lists.newArrayList();
		nvps.add(new BasicNameValuePair("to", mailInfoView.getTo()));
		nvps.add(new BasicNameValuePair("subject", mailInfoView.getSubject()));
		nvps.add(new BasicNameValuePair("content", mailInfoView.getContent()));
		log.debug(format("Mail will be sent.To:%s}Subject:%s|Content:%s", mailInfoView.getTo(),
				mailInfoView.getSubject(), mailInfoView.getContent()));
		
		HttpPost httpPost = new HttpPost(dest);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		HttpResponse resp = httpClient.execute(httpPost);

		if (isOK(resp)) {
			log.debug("Sending mail OK.");
		}
	}
	
	private boolean isOK(HttpResponse resp) {
		return resp.getStatusLine().getStatusCode() == 200;
	}
	
	protected abstract MailInfoView refineMailInfoView(MailInfo mailInfo);
	
}
