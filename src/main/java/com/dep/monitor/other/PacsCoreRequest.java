package com.dep.monitor.other;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;


public class PacsCoreRequest {
//	public static PacsCoreRequest aRequest(String method, String url) {
//		return new PacsCoreRequest(method, url);
//	}
//	
//	public static PacsCoreRequest aStorageRequest(String method, String url){
//		PacsCoreRequest request = new PacsCoreRequest(method, url);
//		request.setUrl(url);
//		return request;
//	}
//	
//	private HttpClient httpClient;
//	private String method;
//	private String url;
//	private Map<String, String> headers = new HashMap<String, String>();
//	private File downloadFile;
//	private File uploadFile;
//	private boolean responseAsString = false;
//	
//	private PacsCoreRequest(String method, String url){
//		this.method = method;
//		this.url = SysConfigWrapper.get("pacs_path", "http://192.168.34.4:8080") + url;
//		HttpClient httpClient = new DefaultHttpClient();
//		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 7200000);
//		this.httpClient = httpClient;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public PacsCoreRequest withHeaders(Map<String, String> headers) {
//		this.headers.putAll(headers);
//		return this;
//	}
//	
//	public PacsCoreRequest withHeader(String name, String value) {
//		this.headers.put(name, value);
//		return this;
//	}
//	
//	public PacsCoreRequest withDownLoadFile(File downloadFile) {
//		this.downloadFile = downloadFile;
//		return this;
//	}
//	
//	public PacsCoreRequest withUploadFile(File uploadFile) {
//		this.uploadFile = uploadFile;
//		return this;
//	}
//	
//	public PacsCoreRequest withResponseAsString() {
//		this.responseAsString = true;
//		return this;
//	}
//
//	public PacsCoreResponse getResponse() throws Exception {
//		if("GET".equals(method)){
//			return get();
//		}
//		if("PUT".equals(method)){
//			return put();
//		}
//		if("DELETE".equals(method)){
//			return delete();
//		}
//		if("HEAD".equals(method)){
//			return head();
//		}
//		throw new RuntimeException("http请求类型不支持");
//	}
//
//	private PacsCoreResponse head() throws Exception {
//		HttpHead head = new HttpHead(url);
//		head.addHeader("Connection", "close");
//		addRequestHeaders(head);
//		HttpResponse response = httpClient.execute(head);
//		return aResponse(response.getStatusLine().getStatusCode())
//							.withHeaders(Arrays.asList(response.getAllHeaders()));
//	}
//
//	private PacsCoreResponse delete() throws Exception{
//		HttpDelete delete = new HttpDelete(url);
//		addRequestHeaders(delete);
//		delete.addHeader("Connection", "close");
//		HttpResponse response = httpClient.execute(delete);
//		return aResponse(response.getStatusLine().getStatusCode());
//	}
//
//	private PacsCoreResponse get() throws Exception {
//		HttpGet get = new HttpGet(url);
//		addRequestHeaders(get);
//		get.addHeader("Connection", "close");
//		HttpResponse response = httpClient.execute(get);
//		if(this.responseAsString){
//			String fileContent = parseToString(response.getEntity().getContent());
//			return aResponse(response.getStatusLine().getStatusCode()).withResponseString(fileContent);
//		}
//		if(this.downloadFile != null && !this.downloadFile.exists()){
//			this.downloadFile.createNewFile();
//			writeResponseToFile(response.getEntity().getContent());
//		}
//		return aResponse(response.getStatusLine().getStatusCode());
//	}
//	
//	private String parseToString(InputStream ins) throws IOException {
//		StringBuffer sbuff = new StringBuffer();
//		InputStreamReader reader = new InputStreamReader(ins);
//		int len = 0;
//		char[] buffer = new char[1024];
//		while((len = reader.read(buffer)) != -1){
//			sbuff.append(buffer, 0, len);
//		}
//		return sbuff.toString();
//	}
//
//	private void writeResponseToFile(InputStream ins) throws IOException, FileNotFoundException {
//		FileOutputStream ops = new FileOutputStream(this.downloadFile);
//		int len = 0;
//		byte[] buffer = new byte[1024];
//		while((len = ins.read(buffer)) != -1){
//			ops.write(buffer, 0, len);
//		}
//		ins.close();
//		ops.close();
//	}
//	
//	private PacsCoreResponse put() throws Exception {
//		HttpPut put = new HttpPut(url);
//		put.addHeader("Connection", "close");
//		addRequestHeaders(put);
//		if(this.uploadFile != null && this.uploadFile.exists()){
//			 put.setEntity(new FileEntity(this.uploadFile , "text/plain; charset=\"UTF-8\""));
//		}
//		HttpResponse response = httpClient.execute(put);
//		return aResponse(response.getStatusLine().getStatusCode());
//	}
//
//	private void addRequestHeaders(HttpRequestBase method) {
//		for(Map.Entry<String, String> header : headers.entrySet()){
//			method.addHeader(header.getKey(), header.getValue());
//		}
//	}
}
