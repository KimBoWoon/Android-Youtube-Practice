package com.videovillage.application.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.videovillage.application.R;

/**
 * Created by secret on 9/4/16.
 */
public class VideoPlayActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_layout);

        Intent intent = getIntent();
        String videoId = intent.getStringExtra("videoID");

        VideoFragment videoFragment =
                (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);
        videoFragment.setVideoId(videoId);
    }
}
