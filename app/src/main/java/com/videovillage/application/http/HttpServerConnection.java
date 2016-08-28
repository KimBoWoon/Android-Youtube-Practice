package com.videovillage.application.http;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by secret on 8/28/16.
 */
public class HttpServerConnection {
    private static DefaultHttpClient ourInstance = new DefaultHttpClient();

    public static DefaultHttpClient getInstance() {
        return ourInstance;
    }

    private HttpServerConnection() {
    }
}
