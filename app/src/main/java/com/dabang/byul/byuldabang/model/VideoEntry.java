package com.dabang.byul.byuldabang.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Null on 2018-03-19.
 */

public class VideoEntry implements Serializable {
    private HashMap<String, Object> snippet;
    private HashMap<String, Object> id;

    public HashMap<String, Object> getSnippet() {
        return snippet;
    }

    public void setSnippet(HashMap<String, Object> snippet) {
        this.snippet = snippet;
    }

    public HashMap<String, Object> getId() {
        return id;
    }

    public void setId(HashMap<String, Object> id) {
        this.id = id;
    }
}
