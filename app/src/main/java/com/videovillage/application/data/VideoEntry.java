package com.videovillage.application.data;

import java.util.Date;

/**
 * Created by secret on 8/27/16.
 */

public class VideoEntry {
    private String text;
    private String videoId;
    private Date date;

    public VideoEntry(String text, String videoId, Date date) {
        this.text = text;
        this.videoId = videoId;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getVideoId() {
        return videoId;
    }

    public Date getDate() {
        return date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}