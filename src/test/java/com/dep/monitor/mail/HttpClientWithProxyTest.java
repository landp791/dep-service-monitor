package com.dep.monitor.mail;

import static java.lang.String.format;

import java.util.List;

import org.apache.http.HttpEntity;
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
import org.apache.http.util.EntityUtils;

import com.dep.monitor.model.MailInfoView;
import com.google.common.collect.Lists;

public class HttpClientWithProxyTest {
    DefaultHttpClient httpclient = new DefaultHttpClient();

    public void should_OK_when_access_baidu_with_proxy() throws Exception {
        try {
            httpclient.getCredentialsProvider().setCredentials(new AuthScope("10.37.84.124", 8080),
                    new UsernamePasswordCredentials("landongping791", "ldpPA&(!"));

            HttpHost targetHost = new HttpHost("www.depblog.sinaapp.com", 80, "http");
            HttpHost proxy = new HttpHost("10.37.84.124", 8080);

            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            HttpPost httppost = new HttpPost("/api/mail/forward");
            
//            List<NameValuePair> nvps = prepareRequestParas(mailInfoView);
//            
//            HttpPost httpPost = new HttpPost(dest);
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
//            HttpResponse resp = httpClient.execute(httpPost);
//
//            if (isOK(resp)) {
//                log.debug("Sending mail OK.");
//            }
            
            System.out.println("executing request: " + httppost.getRequestLine());
            System.out.println("via proxy: " + proxy);
            System.out.println("to target: " + targetHost);
            
            HttpResponse response = httpclient.execute(targetHost, httppost);
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
            }
            EntityUtils.consume(entity);

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }
    
    private List<NameValuePair> prepareRequestParas(MailInfoView mailInfoView) {
        List<NameValuePair> nvps = Lists.newArrayList();
        nvps.add(new BasicNameValuePair("to", mailInfoView.getTo()));
        nvps.add(new BasicNameValuePair("subject", mailInfoView.getSubject()));
        nvps.add(new BasicNameValuePair("content", mailInfoView.getContent()));
        return nvps;
    }
    
    private MailInfoView aMailInfoView() {
        return null;
    }
}
