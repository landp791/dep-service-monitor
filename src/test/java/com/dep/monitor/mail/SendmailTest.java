package com.dep.monitor.mail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class SendmailTest {

    private static String MAIL_SEND_URL = "http://mailsend.sinaapp.com/mailsend.do";

    private static String BAIDU_URL = "http://pws.paic.com.cn";

    private static String BASE_URL = "http://10.20.8.17:8080";

    // private static String MAIL_SEND_URL = "http://localhost:8080/mailsend/mailsend.do";

    public static void main(String[] args) throws Exception {
        testBase2();
    }

    private static void testBase() throws Exception {
        // DefaultHttpClient client = getProxyHttpClient2();
        DefaultHttpClient client = getHttpClient();

//        HttpGet get = new HttpGet(BAIDU_URL);
//        HttpResponse response = client.execute(get);
//        System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
//        InputStream in = response.getEntity().getContent();
//        System.out.println("result:" + readString(in).length());
        
        
        client.getCredentialsProvider().setCredentials(new AuthScope("10.37.84.124", 8080),
                new UsernamePasswordCredentials("landongping791", "ldpPA&(!"));
        // 访问的目标站点，端口和协议
        HttpHost targetHost = new HttpHost("www.baidu.com", 80, "http");
        // 代理的设置
        HttpHost proxy = new HttpHost("10.37.84.124", 8080);
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        // 目标地址
        HttpGet httpget = new HttpGet("/adsense/login/zh_CN/?");
        System.out.println("目标: " + targetHost);
        System.out.println("请求: " + httpget.getRequestLine());
        System.out.println("代理: " + proxy);
        // 执行
        HttpResponse response2 = client.execute(targetHost, httpget);

        client.getConnectionManager().shutdown();
    }
    
    private static void testBase2() throws Exception {
        DefaultHttpClient client = getHttpClient();
        client.getCredentialsProvider().setCredentials(new AuthScope("10.37.84.124", 8080),
                new UsernamePasswordCredentials("landongping791", "ldpPA&(!"));
        // 访问的目标站点，端口和协议
        HttpHost targetHost = new HttpHost("zhidao.baidu.com", 80, "http");
        // 代理的设置
        HttpHost proxy = new HttpHost("10.37.84.124", 8080);
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        // 目标地址
        HttpGet httpget = new HttpGet("/link?url=by9NfpadrYIL6Qrvw29LOV71QloPU48Bkg8VekFlfTCoCKulsfhZb1Rd70XYNAPHTAilTtYWdIzUX8of_yU1uq");
        System.out.println("目标: " + targetHost);
        System.out.println("请求: " + httpget.getRequestLine());
        System.out.println("代理: " + proxy);
        // 执行
        HttpResponse response = client.execute(targetHost, httpget);
        
      System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
      InputStream in = response.getEntity().getContent();
      System.out.println("result:" + readString(in).length());

        client.getConnectionManager().shutdown();
    }

    private static void testMail() throws Exception {
        String to = "landongping791@pingan.com";
        String title = "功能测试";
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateformat1.format(new Date());
        String content = "看到这个就是收到了邮件了吧！" + time + "," + MAIL_SEND_URL;

        String sendAccount = "landongpingpub@163.com";
        String sendPassword = "LDP163pwd";
        String sendSmtp = "smtp.163.com";
        String sendPort = "25";

        DefaultHttpClient client = getProxyHttpClient();
        // DefaultHttpClient client = getHttpClient();

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        NameValuePair pair1 = new BasicNameValuePair("to", to);
        NameValuePair pair2 = new BasicNameValuePair("title", title);
        NameValuePair pair3 = new BasicNameValuePair("content", content);
        NameValuePair pair4 = new BasicNameValuePair("sendAccount", sendAccount);
        NameValuePair pair5 = new BasicNameValuePair("sendPassword", sendPassword);
        NameValuePair pair6 = new BasicNameValuePair("sendSmtp", sendSmtp);
        NameValuePair pair7 = new BasicNameValuePair("sendPort", sendPort);
        list.add(pair1);
        list.add(pair2);
        list.add(pair3);
        list.add(pair4);
        list.add(pair5);
        list.add(pair6);
        list.add(pair7);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
        HttpPost post = new HttpPost(MAIL_SEND_URL);
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
        InputStream in = response.getEntity().getContent();
        System.out.println("result:" + readString(in));

        client.getConnectionManager().shutdown();
    }

    private static void testInternet() throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        // 认证的数据
        // 我这里是瞎写的，请根据实际情况填写
        httpclient.getCredentialsProvider().setCredentials(new AuthScope("10.60.8.20", 8080),
                new UsernamePasswordCredentials("username", "password"));
        // 访问的目标站点，端口和协议
        HttpHost targetHost = new HttpHost("www.google.com", 443, "https");
        // 代理的设置
        HttpHost proxy = new HttpHost("10.60.8.20", 8080);
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        // 目标地址
        HttpGet httpget = new HttpGet("/adsense/login/zh_CN/?");
        System.out.println("目标: " + targetHost);
        System.out.println("请求: " + httpget.getRequestLine());
        System.out.println("代理: " + proxy);
        // 执行
        HttpResponse response = httpclient.execute(targetHost, httpget);
        HttpEntity entity = response.getEntity();
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (entity != null) {
            System.out.println("Response content length: " + entity.getContentLength());
        }
        // 显示结果
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        if (entity != null) {
            entity.consumeContent();
        }
    }

    private static DefaultHttpClient getProxyHttpClient() {
        String proxyHost = "10.37.84.124";
        int proxyPort = 8080;
        String proxyUserName = "landongping791";
        String proxyPassword = "ldpPA&(!";

        DefaultHttpClient client = new DefaultHttpClient();
        AuthScope authscope = new AuthScope(proxyHost, proxyPort);
        Credentials credentials = new UsernamePasswordCredentials(proxyUserName, proxyPassword);
        client.getCredentialsProvider().setCredentials(authscope, credentials);
        return client;
    }

    private static DefaultHttpClient getProxyHttpClient2() {
        String proxyHost = "10.37.84.124";
        int proxyPort = 8080;
        String proxyUserName = "landongping791";
        String proxyPassword = "ldpPA&(!";

        DefaultHttpClient client = new DefaultHttpClient();
        AuthScope authscope = new AuthScope(proxyHost, proxyPort);
        Credentials credentials = new UsernamePasswordCredentials(proxyUserName, proxyPassword);
        client.getCredentialsProvider().setCredentials(authscope, credentials);
        return client;
    }

    private static DefaultHttpClient getHttpClient() {
        DefaultHttpClient client = new DefaultHttpClient();
        return client;
    }

    protected static String readString(InputStream in) throws Exception {
        byte[] data = new byte[1024];
        int length = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while ((length = in.read(data)) != -1) {
            bout.write(data, 0, length);
        }
        return new String(bout.toByteArray(), "UTF-8");

    }

}
