package com.videovillage.application.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by secret on 9/14/16.
 */
public class HttpPresenter implements HttpContract.UserAction {
    private HttpContract.View mHttpView;
    private HttpModel mHttpModel;

    private URL url;
    private HttpURLConnection conn;
    private StringBuilder responseString;

    public HttpPresenter(HttpContract.View view) {
        this.mHttpView = view;
        this.mHttpModel = new HttpModel();
    }

    public HttpPresenter() {
        this.mHttpModel = new HttpModel();
    }

    @Override
    public void initHttpConnection(String urlString) {
        try {
            this.url = new URL(urlString);
            this.conn = (HttpURLConnection) url.openConnection();
            this.responseString = new StringBuilder();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getJSONString() {
        try {
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;

                while ((line = br.readLine()) != null) {
                    responseString = mHttpModel.stringAppend(responseString, line.trim());
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseString.toString();
    }
}
