package com.videovillage.application.thread;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.videovillage.application.R;
import com.videovillage.application.adapter.PageAdapter;
import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.data.SharedStore;
import com.videovillage.application.data.VideoEntry;
import com.videovillage.application.http.HttpPresenter;
import com.videovillage.application.mainactivity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by secret on 8/28/16.
 */
public class VideosSearchThread extends AsyncTask<String, Integer, String> {
    private Context context;
    private ProgressBar progress;
    private String urlString;
    private PageAdapter pageAdapter;
    private String responseString;
    private String youtubeServerKey;

    @Override
    protected String doInBackground(String... params) {
        String url = SharedStore.getString(context, Constant.VIDEO_SEARCH_URL);
        Pattern pattern = Pattern.compile("STRING_CHANGE");
        Matcher matcher = pattern.matcher(url);

        this.youtubeServerKey = SharedStore.getString(context, Constant.YOUTUBE_SERVER_API_KEY);
        this.urlString = matcher.replaceFirst(params[0]) + youtubeServerKey;

        Log.i("FCM", "urlString : " + urlString);

        try {
            HttpPresenter conn = new HttpPresenter();
            conn.initHttpConnection(urlString);

            responseString = conn.getJSONString();

            Log.d("responseString", "" + responseString);

            JSONObject object = new JSONObject(responseString);
            JSONArray jarray = new JSONArray(object.getString("items"));

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                String id = jObject.getString("id");
                JSONObject jsonObject = new JSONObject(id);
                String videoId = jsonObject.getString("videoId");

                String snippet = jObject.getString("snippet");
                jsonObject = new JSONObject(snippet);

                String title = jsonObject.getString("title");

                String description = jsonObject.getString("description");

                String date = jsonObject.getString("publishedAt");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.s'Z'", Locale.KOREAN);
                Date publishedAt = sdf.parse(date);

                DataManager.getDataManager().getVideoEntry().add(new VideoEntry(title, videoId, publishedAt, description));
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Collections.sort(DataManager.getDataManager().getVideoEntry(), new Comparator<VideoEntry>() {
            @Override
            public int compare(VideoEntry v1, VideoEntry v2) {
                return v2.getDate().compareTo(v1.getDate());
            }
        });

        return responseString;
    }

    public VideosSearchThread(Context context, PageAdapter pageAdapter) {
        super();
        this.context = context;
        this.pageAdapter = pageAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = (ProgressBar) ((MainActivity) context).findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progress.setVisibility(View.GONE);
        pageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progress.setVisibility(View.GONE);
    }
}