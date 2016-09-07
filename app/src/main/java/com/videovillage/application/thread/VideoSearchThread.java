package com.videovillage.application.thread;

import android.util.Log;

import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.data.VideoEntry;
import com.videovillage.application.http.HttpServerConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by secret on 9/7/16.
 */
public class VideoSearchThread {
    private String keyword;
    private String url;

    public VideoSearchThread(String keyword) {
        this.keyword = keyword;
        this.url = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + keyword + "&fields=items/snippet/title,items/snippet/description&key=" + Constant.YOUTUBE_SERVER_API_KET;
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
        private String urlString;
        private String responseString;

        public DownThread(String urlString) {
            this.urlString = urlString;
        }

        public void run() {
            HttpServerConnection conn = new HttpServerConnection(urlString);

            responseString = conn.getJSONString();
            Log.i("VideoSearchThread", responseString);

            try {
                JSONObject object = new JSONObject(responseString);
                JSONArray jarray = new JSONArray(object.getString("items"));

                JSONObject jObject = jarray.getJSONObject(0);

                String snippet = jObject.getString("snippet");
                JSONObject jsonObject = new JSONObject(snippet);

                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");

                DataManager.getDataManager().setVideoInfo(new VideoEntry(title, "", new Date(), description));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}