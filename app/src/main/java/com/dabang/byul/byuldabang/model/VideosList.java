package com.dabang.byul.byuldabang.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Null on 2018-03-19.
 */

public class VideosList implements Serializable {
    private ArrayList<VideoEntry> items;

    public ArrayList<VideoEntry> getItems() {
        return items;
    }

    public void setItems(ArrayList<VideoEntry> items) {
        this.items = items;
    }
}