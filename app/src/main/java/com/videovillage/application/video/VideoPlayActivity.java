package com.videovillage.application.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.videovillage.application.R;
import com.videovillage.application.data.VideoEntry;

/**
 * Created by secret on 9/4/16.
 */
public class VideoPlayActivity extends Activity {
    AQuery aq = new AQuery(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_layout);

        Intent intent = getIntent();
        VideoEntry videoEntry = (VideoEntry) intent.getSerializableExtra("VideoEntry");

        VideoFragment videoFragment =
                (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);
        videoFragment.setVideoId(videoEntry.getVideoId());

        aq.id(R.id.video_description_text).text(videoEntry.getDescription());
    }
}