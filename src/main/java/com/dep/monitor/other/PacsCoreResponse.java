package com.dep.monitor.other;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

public class PacsCoreResponse {
	private int status;
	private String responseString;
	private Map<String, String> headers = new HashMap<String, String>();
	
	private PacsCoreResponse(int status){
		this.status = status;
	}

	public static PacsCoreResponse aResponse(int status) {
		return new PacsCoreResponse(status);
	}

	public int getStatus() {
		return status;
	}

	public PacsCoreResponse withHeaders(List<Header> headerList) {
		if(headerList != null){
			for(Header header : headerList){
				this.headers.put(header.getName(), header.getValue());
			}
		}
		return this;
	}

    public PacsCoreResponse withHeaders(Header[] headers) {
        for (Header header : headers) {
            this.headers.put(header.getName(), header.getValue());
        }
        return this;
    }

	public PacsCoreResponse withResponseString(String fileContent) {
		this.responseString = fileContent;
		return this;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getResponseString() {
		return responseString;
	}

    public String getHeaderBy(String name) {
        return headers.get(name);
    }
}
