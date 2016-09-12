package com.videovillage.application.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by secret on 8/28/16.
 */
public class HttpServerConnection {
    private HttpURLConnection conn;
    private StringBuilder responseString;
    private URL url;

    public HttpURLConnection getInstance() {
        return conn;
    }

    public HttpServerConnection(String urlString) {
        try {
            this.url = new URL(urlString);
            this.conn = (HttpURLConnection) url.openConnection();
            this.responseString = new StringBuilder();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getJSONString() {
        try {
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;

                while ((line = br.readLine()) != null) {
//                    responseString.append(line).append("\n");
                    responseString.append(line.trim());
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseString.toString();
    }
}
