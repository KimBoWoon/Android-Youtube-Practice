package com.videovillage.application.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by secret on 8/27/16.
 */

public class VideoEntry implements Serializable {
    private String title;
    private String videoId;
    private Date date;
    private String description;

    public VideoEntry(String title, String videoId, Date date, String description) {
        this.title = title;
        this.videoId = videoId;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoId() {
        return videoId;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}