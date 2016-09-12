package com.videovillage.application.data;

import java.util.ArrayList;

/**
 * Created by secret on 8/28/16.
 */
public class DataManager {
    private ArrayList<VideoEntry> videoEntry = new ArrayList<VideoEntry>();
    private VideoEntry videoInfo;
    private static DataManager dataManager = new DataManager();

    public DataManager() {
        videoEntry = new ArrayList<VideoEntry>();
    }

    public ArrayList<VideoEntry> getVideoEntry() {
        return videoEntry;
    }

    public VideoEntry getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoEntry videoInfo) {
        this.videoInfo = videoInfo;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }
}
