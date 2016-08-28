package com.videovillage.application.data;

import java.util.ArrayList;

/**
 * Created by secret on 8/28/16.
 */
public class DataManager {
    private ArrayList<VideoEntry> videoEntry = new ArrayList<VideoEntry>();
    private static DataManager dataManager = new DataManager();

    public DataManager() {
        videoEntry = new ArrayList<VideoEntry>();
    }

    public ArrayList<VideoEntry> getVideoEntry() {
        return videoEntry;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }
}
