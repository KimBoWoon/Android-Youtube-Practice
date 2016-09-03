package com.videovillage.application.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.data.VideoEntry;
import com.videovillage.application.http.HttpServerConnection;
import com.videovillage.application.video.VideoListFragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by secret on 8/28/16.
 */
public class DataGetThread extends AsyncTask<String, Integer, String> {
    private Context context;
    private ProgressDialog progress;
    private String urlString;
    private VideoListFragment listFragment;

    @Override
    protected String doInBackground(String... params) {
        DefaultHttpClient httpClient = HttpServerConnection.getInstance();
        String responseString = null;
        urlString = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + params[0] + "&key=" + Constant.YOUTUBE_SERVER_API_KET + "&maxResults=10";

        try {
            HttpGet httpGet = new HttpGet(urlString);

            HttpResponse response = httpClient.execute(httpGet);
            responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

            Log.d("DataGetThread", responseString);

            JSONObject object = new JSONObject(responseString);
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
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("IOException", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("JSONException", e.getLocalizedMessage());
            e.printStackTrace();
        }

        return responseString;
    }

    public DataGetThread(Context context, VideoListFragment listFragment) {
        super();
        this.context = context;
        this.listFragment = listFragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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