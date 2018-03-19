package com.dabang.byul.byuldabang.data;

import com.dabang.byul.byuldabang.model.VideosList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Null on 2018-03-19.
 */

public class DataManager {
    private List<VideosList> videoEntry = new ArrayList<VideosList>();
    private static DataManager dataManager = new DataManager();

    private static class Singleton {
        private static final DataManager instance = new DataManager();
    }

    public static DataManager getInstance() {
        return Singleton.instance;
    }

    public List<VideosList> getVideoEntry() {
        return videoEntry;
    }

    public void setVideoEntry(List<VideosList> videoEntry) {
        this.videoEntry = videoEntry;
    }
}
