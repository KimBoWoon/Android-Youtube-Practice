package com.videovillage.application.thread;

import android.util.Log;

import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.VideoEntry;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.http.HttpServerConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by secret on 8/28/16.
 */
public class DataGetThread {
    private String keyword;
    private String url;

    public DataGetThread(String keyword) {
        this.keyword = keyword;
        this.url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + keyword + "&key=" + Constant.YOUTUBE_SERVER_API_KET + "&maxResults=50";
        startGetThread();
    }

    public DataGetThread(String keyword, String url) {
        this.keyword = keyword;
        this.url = url;
        startGetThread();
    }

    private void startGetThread() {
        DownThread thread = new DownThread(url);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    class DownThread extends Thread {
        private String url;

        public DownThread(String url) {
            this.url = url;
        }

        public void run() {
            Log.i("URLNull", "" + url);
            HttpGet get = new HttpGet(url);
            DefaultHttpClient client = HttpServerConnection.getInstance();
            try {
                client.execute(get, mResHandler);
            } catch (Exception ignored) {
                ;
            }
        }
    }

    ResponseHandler<String> mResHandler = new ResponseHandler<String>() {
        public String handleResponse(HttpResponse response) {
            StringBuilder html = new StringBuilder();
            String imgUrl = "http://52.79.147.144/images/profile/";

            try {
                BufferedReader br = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));
                for (; ; ) {
                    String line = br.readLine();
                    if (line == null) break;
                    html.append(line + '\n');
                }
                br.close();

                String str = html.toString();
                Log.i("StringBuilder", str);

                JSONObject object = new JSONObject(str);
                JSONArray jarray = new JSONArray(object.getString("items"));

                for (int i = 1; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    String id = jObject.getString("id");
                    JSONObject jsonObject = new JSONObject(id);
                    String videoId = jsonObject.getString("videoId");

                    String snippet = jObject.getString("snippet");
                    jsonObject = new JSONObject(snippet);
                    String title = jsonObject.getString("title");

                    DataManager.getDataManager().getVideoEntry().add(new VideoEntry(title, videoId));
                }
            } catch (Exception ignored) {
                ;
            }
            return html.toString();
        }
    };
}
