package com.dabang.byul.byuldabang.http;


import com.dabang.byul.byuldabang.data.StreamerType;

public interface ServiceInterface {
    void requestYoutubeVideosList(final VolleyCallback callback);
    void requestYoutubeVideosList(StreamerType type, final VolleyCallback callback);
}
