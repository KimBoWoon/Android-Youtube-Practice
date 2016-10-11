package com.videovillage.application.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.data.VideoEntry;
import com.videovillage.application.http.HttpPresenter;
import com.videovillage.application.video.VideoListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by secret on 8/28/16.
 */
public class VideosSearchThread extends AsyncTask<String, Integer, String> {
    private Context context;
    private ProgressDialog progress;
    private String urlString;
    private VideoListFragment listFragment;
    private String responseString;

    @Override
    protected String doInBackground(String... params) {
        urlString = "https://www.googleapis.com/youtube/v3/search?part=id%2Csnippet&channelId=" + params[0] + "&maxResults=10&order=date&fields=items/snippet/title,items/snippet/publishedAt,items/id/videoId&type=video&key=" + Constant.YOUTUBE_SERVER_API_KEY;

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

                String date = jsonObject.getString("publishedAt");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.s'Z'", Locale.KOREAN);
                Date publishedAt = sdf.parse(date);

//                String description = jsonObject.getString("description");

                DataManager.getDataManager().getVideoEntry().add(new VideoEntry(title, videoId, publishedAt, ""));
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

    public VideosSearchThread(Context context, VideoListFragment listFragment) {
        super();
        this.context = context;
        this.listFragment = listFragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setTitle("데이터 수신중...");
        progress.setMessage("잠시만 기다려주세요...");
        progress.setCancelable(false);
        progress.setProgress(0);
        progress.setButton(DialogInterface.BUTTON_NEGATIVE, "취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel(true);
            }
        });
        progress.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progress.dismiss();
        listFragment.getAdapter().notifyDataSetInvalidated();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progress.setProgress(values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progress.dismiss();
    }
}