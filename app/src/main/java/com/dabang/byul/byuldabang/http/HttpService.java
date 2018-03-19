package com.dabang.byul.byuldabang.http;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dabang.byul.byuldabang.data.Constant;
import com.dabang.byul.byuldabang.data.DataManager;
import com.dabang.byul.byuldabang.data.StreamerType;
import com.dabang.byul.byuldabang.model.VideosList;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class HttpService implements ServiceInterface {
    private Gson gson = new Gson();

    private String makeURL(StreamerType type) {
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("https");
        uri.encodedAuthority("www.googleapis.com");
        uri.path("youtube/v3/search");
        uri.appendQueryParameter("part", "snippet");
        uri.appendQueryParameter("order", "date");
        uri.appendQueryParameter("type", "video");
        uri.appendQueryParameter("maxResults", "50");
        uri.appendQueryParameter("key", Constant.YOUTUBE_API_KEY);

        switch (type) {
            case ALL:
                uri.appendQueryParameter("channelId", "UCizAD_gtbKrE6Sgajer_Lvg");
                uri.appendQueryParameter("channelId", "UC587UQIVLWBX5TsoK4l7RqQ");
                uri.appendQueryParameter("channelId", "UCkkVe8okaSUNfBS5sxorrIQ");
                uri.appendQueryParameter("channelId", "UCPsdKTSJmqP5pj9cIiKSNJw");
                uri.appendQueryParameter("channelId", "UC0BqJvnq0FZcBUKGQTGFFwg");
                break;
            case BOMDALSAE:
                uri.appendQueryParameter("channelId", "UC587UQIVLWBX5TsoK4l7RqQ");
                break;
            case WHITEPANG:
                uri.appendQueryParameter("channelId", "UCizAD_gtbKrE6Sgajer_Lvg");
                break;
            case MINGA:
                uri.appendQueryParameter("channelId", "UCkkVe8okaSUNfBS5sxorrIQ");
                break;
            case SOOK:
                uri.appendQueryParameter("channelId", "UCPsdKTSJmqP5pj9cIiKSNJw");
                break;
            case BOMI:
                uri.appendQueryParameter("channelId", "UC0BqJvnq0FZcBUKGQTGFFwg");
                break;
        }

        Log.i("uri", uri.toString());

        return uri.build().toString();
    }

    private HttpOption httpOptionSetting(String apiKey) {
        HttpOption option = new HttpOption();

        option.setBodyContentType("application/json");
        option.setContentType("application/json");
        option.setSecretKey(apiKey);

        return option;
    }

    public void requestYoutubeVideosList(final VolleyCallback callback) {
        String url = makeURL(StreamerType.ALL);

        JsonCustomRequest request = new JsonCustomRequest(
                Request.Method.GET,
                url,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", response.toString());
                        try {
                            Log.i("Response", response.get("items").toString());
                            List<VideosList> videos = Arrays.asList(gson.fromJson(response.toString(), VideosList.class));
                            DataManager.getInstance().setVideoEntry(videos);
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error");
                    }
                }
        );

        addRequestQueue(request);
    }

    public void requestYoutubeVideosList(StreamerType type, final VolleyCallback callback) {
        String url = makeURL(type);

        JsonCustomRequest request = new JsonCustomRequest(
                Request.Method.GET,
                url,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", response.toString());
                        try {
                            Log.i("Response", response.get("items").toString());
                            List<VideosList> videos = Arrays.asList(gson.fromJson(response.toString(), VideosList.class));
                            DataManager.getInstance().setVideoEntry(videos);
                            Log.i("dataSize", String.valueOf(DataManager.getInstance().getVideoEntry().get(0).getItems().size()));
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error");
                    }
                }
        );

        addRequestQueue(request);
    }

    private void addRequestQueue(JsonCustomRequest request) {
        try {
            VolleyManager.getInstance().getRequestQueue().add(request);
        } catch (IllegalAccessException e) {
            Log.i("IllegalAccessException", e.getMessage());
        }
    }
}