package com.dep.monitor.mail;

import static java.lang.String.format;
import static com.dep.monitor.util.HttpClientHelper.isOK;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
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
	
	@Value("${proxy_host}")
	private String proxyHost;
	
	@Value("${proxy_port}")
	private int proxyPort;

	@Value("${proxy_user_name}")
	private String proxyUserName;

	@Value("${proxy_password}")
	private String proxyPassword;

	@Value("${destination}")
	private String dest;

	private DefaultHttpClient httpClient;

	@PostConstruct
	public void init() {
		httpClient = new DefaultHttpClient();

		// set proxy access
		httpClient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort),
                new UsernamePasswordCredentials(proxyUserName, proxyPassword));
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
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
		List<NameValuePair> nvps = prepareRequestParas(mailInfoView);
		
		HttpPost httpPost = new HttpPost(dest);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		HttpResponse resp = httpClient.execute(httpPost);

		if (isOK(resp)) {
			log.debug("Sending mail OK.");
		}
	}

	private List<NameValuePair> prepareRequestParas(MailInfoView mailInfoView) {
		List<NameValuePair> nvps = Lists.newArrayList();
		nvps.add(new BasicNameValuePair("to", mailInfoView.getTo()));
		nvps.add(new BasicNameValuePair("subject", mailInfoView.getSubject()));
		nvps.add(new BasicNameValuePair("content", mailInfoView.getContent()));
		log.debug(format("Mail will be sent.To:%s}Subject:%s|Content:%s", mailInfoView.getTo(),
				mailInfoView.getSubject(), mailInfoView.getContent()));
		return nvps;
	}
	
	protected abstract MailInfoView refineMailInfoView(MailInfo mailInfo);
	
}
