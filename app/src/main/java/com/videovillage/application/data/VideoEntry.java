package com.videovillage.application.data;

/**
 * Created by secret on 8/27/16.
 */

public class VideoEntry {
    private String text;
    private String videoId;

    public VideoEntry(String text, String videoId) {
        this.text = text;
        this.videoId = videoId;
    }

    public String getText() {
        return text;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}