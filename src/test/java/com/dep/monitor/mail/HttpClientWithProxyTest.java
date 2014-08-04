package com.dep.monitor.mail;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.dep.monitor.model.MailInfo;
import com.google.common.collect.Lists;

public class HttpClientWithProxyTest {
    DefaultHttpClient httpclient = new DefaultHttpClient();
    
//    @Test
    public void should_not_OK_when_acc_badidu_not_via_proxy() throws Exception {
        try {
            HttpGet httpget = new HttpGet("http://www.baidu.com/");
            HttpResponse response = httpclient.execute(httpget);

            assertEquals(false,isOK(response));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
    
//    @Test
    public void should_OK_when_acc_badidu_with_proxy() throws Exception {
        try {
            httpclient.getCredentialsProvider().setCredentials(new AuthScope("10.37.84.124", 8080),
                    new UsernamePasswordCredentials("landongping791", "ldpPA)(*"));

            HttpHost proxy = new HttpHost("10.37.84.124", 8080);
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            HttpGet httpget = new HttpGet("http://www.baidu.com/");
            HttpResponse response = httpclient.execute(httpget);

            assertEquals(true,isOK(response));
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
    

//    @Test
    public void should_OK_when_send_mail_with_proxy() throws Exception {
        try {
            httpclient.getCredentialsProvider().setCredentials(new AuthScope("10.37.84.124", 8080),
                    new UsernamePasswordCredentials("landongping791", "ldpPA)(*"));

            HttpHost targetHost = new HttpHost("www.depblog.sinaapp.com", 80, "http");
            HttpHost proxy = new HttpHost("10.37.84.124", 8080);

            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            HttpPost httpPost = new HttpPost(buildReqUrl());
            
//            List<NameValuePair> nvps = prepareRequestParas(aMailInfoView());
            
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse response = httpclient.execute(targetHost, httpPost);

            assertEquals(true,isOK(response));
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
    
    private String buildReqUrl() {
        String url = "/api/mail/forward?to=landongping@pingan.com&subject=TestEmail&content=JustATest";
        
        
        return url;
    }
    
    private List<NameValuePair> prepareRequestParas(MailInfo mailInfoView) {
        List<NameValuePair> nvps = Lists.newArrayList();
        nvps.add(new BasicNameValuePair("to", mailInfoView.getTo()));
        nvps.add(new BasicNameValuePair("subject", mailInfoView.getSubject()));
        nvps.add(new BasicNameValuePair("content", mailInfoView.getContent()));
        return nvps;
    }
    
    private MailInfo aMailInfoView() {
        MailInfo mv = new MailInfo();
        mv.setContent("Just a test!");
        mv.setSubject("Test email");
        mv.setTo("landongping791@pingan.com");
        
        return mv;
    }
    
    private boolean isOK(HttpResponse resp) {
        return resp.getStatusLine().getStatusCode() == 200;
    }
}
