package com.videovillage.application;

/**
 * Created by secret on 8/27/16.
 */
public interface Constant {
    String YOUTUBE_API_KEY = "AIzaSyBg4csrBzwJBe8BzhkyqNTVtIFfJCSy5D0";
    /** The duration of the animation sliding up the video in portrait. */
    int ANIMATION_DURATION_MILLIS = 300;
    /** The padding between the video list and the video in landscape orientation. */
    int LANDSCAPE_VIDEO_PADDING_DP = 5;
    /** The request code when calling startActivityForResult to recover from an API service error. */
    int RECOVERY_DIALOG_REQUEST = 1;
}
