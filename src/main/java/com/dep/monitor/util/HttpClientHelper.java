package com.dep.monitor.util;

import org.apache.http.HttpResponse;

public class HttpClientHelper {

    public static boolean isOK(HttpResponse resp) {
        return resp.getStatusLine().getStatusCode() == 200;
    }
}
