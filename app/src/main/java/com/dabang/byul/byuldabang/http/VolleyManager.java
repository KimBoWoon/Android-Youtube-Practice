package com.dabang.byul.byuldabang.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyManager {
    private RequestQueue rq;
    private ImageLoader il;
    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

    private VolleyManager() {
        il = new ImageLoader(rq, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    private static class Singleton {
        private static final VolleyManager INSTANCE = new VolleyManager();
    }

    public static VolleyManager getInstance() {
        return Singleton.INSTANCE;
    }

    public RequestQueue getRequestQueue() throws IllegalAccessException {
        if (rq == null) {
            throw new IllegalAccessException("Need Initialize Request Queue");
        }
        return rq;
    }

    public void setRequestQueue(Context context) {
        this.rq = Volley.newRequestQueue(context);
    }

    public ImageLoader getImageLoader() {
        return il;
    }
}