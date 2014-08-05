package com.dep.monitor.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class HttpClientHelper {

    public static boolean isOK(HttpResponse resp) throws IOException {
        EntityUtils.consume(resp.getEntity());
        return resp.getStatusLine().getStatusCode() == 200;
    }
}
