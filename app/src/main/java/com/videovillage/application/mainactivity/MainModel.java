package com.videovillage.application.mainactivity;

/**
 * Created by secret on 9/14/16.
 */
public class MainModel {
    public String videoNameTrim(String videoName) {
        return videoName.replace(" ", "%20");
    }
}
