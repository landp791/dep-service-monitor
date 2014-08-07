package com.dep.monitor.mail;

import static com.dep.monitor.util.HttpClientHelper.isOK;
import static java.lang.String.format;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dep.monitor.model.MailInfo;
import com.dep.monitor.model.MonitorInfo;
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

	private List<DefaultHttpClient> httpClients = Lists.newArrayList();

	@PostConstruct
	public void init() {
	    log.info("proxyHost:" + proxyHost);
	    String[] proxyHosts = StringUtils.split(proxyHost,",");
	    for (String oneProxyHost : proxyHosts) {
	        log.info("construct httpClient by proxy host:" + oneProxyHost);
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        setProxyForHttpClient(httpClient, oneProxyHost);
	        httpClients.add(httpClient);
	    }
	}
	
	private void setProxyForHttpClient(DefaultHttpClient httpClient, String oneProxyHost) {
	        // set proxy access
	        httpClient.getCredentialsProvider().setCredentials(new AuthScope(oneProxyHost, proxyPort),
	                new UsernamePasswordCredentials(proxyUserName, proxyPassword));
	        HttpHost proxy = new HttpHost(oneProxyHost, proxyPort);
	        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

	@PreDestroy
	public void onDestroy() {
		if (httpClients == null) {
			return;
		}
		
		for (DefaultHttpClient httpClient : httpClients) {
		    httpClient.getConnectionManager().shutdown();
		}
	}

	@Override
	public void send(MonitorInfo mailInfo) throws Exception {
	    log.info("Send mail to " + Arrays.asList(mailInfo.getToMailAddrs()));
		MailInfo mailInfoView = refineMailInfoView(mailInfo);
		List<NameValuePair> nvps = prepareRequestParas(mailInfoView);
		
		HttpPost httpPost = new HttpPost(dest);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		
		if (tryAllProxyToSendMail(httpPost)) {
			log.debug("Sending mail OK.");
		} else {
		    log.error("Sending mail fail!!.");
		}
	}

	private boolean tryAllProxyToSendMail(HttpPost httpPost) throws ClientProtocolException, IOException {
        for (DefaultHttpClient httpClient : httpClients) {
            HttpResponse resp = httpClient.execute(httpPost);
            if (isOK(resp)) {
                return true;
            } 
        }
        return false;
    }

    private List<NameValuePair> prepareRequestParas(MailInfo mailInfoView) throws UnsupportedEncodingException {
		List<NameValuePair> nvps = Lists.newArrayList();
		nvps.add(new BasicNameValuePair("to", mailInfoView.getTo()));
		nvps.add(new BasicNameValuePair("subject", new String(mailInfoView.getSubject().getBytes(), "UTF-8")));
		nvps.add(new BasicNameValuePair("content", mailInfoView.getContent()));
		log.debug(format("Mail will be sent.To:%s|Subject:%s|Content:%s", mailInfoView.getTo(),
		        new String(mailInfoView.getSubject().getBytes(), "UTF-8"), mailInfoView.getContent()));
		return nvps;
	}
	
	protected abstract MailInfo refineMailInfoView(MonitorInfo mailInfo);
	
}
