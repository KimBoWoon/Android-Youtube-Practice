package com.dabang.byul.byuldabang.video;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.dabang.byul.byuldabang.R;
import com.dabang.byul.byuldabang.data.Constant;
import com.dabang.byul.byuldabang.model.VideoEntry;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Null on 2018-03-19.
 */

public class VideoPlayActivity extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {
    private YouTubePlayer player;
    private YouTubePlayerView playerView;
    private String videoId;
    private boolean isFullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_layout);

        Intent intent = getIntent();
        VideoEntry videoEntry = (VideoEntry) intent.getSerializableExtra("VideoEntry");

        playerView = (YouTubePlayerView) findViewById(R.id.video_player_youtube_player);
        playerView.initialize(Constant.YOUTUBE_API_KEY, this);
        setVideoId(videoEntry.getId().get("videoId").toString());

        TextView description = findViewById(R.id.video_description_text);
        description.setText(videoEntry.getSnippet().get("description").toString());
        isFullscreen = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.release();
        }
        super.onDestroy();
    }

    public void setVideoId(String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            if (player != null) {
                player.cueVideo(videoId);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {
        this.player = player;
        player.setOnFullscreenListener(this);
        if (!restored && videoId != null) {
            player.cueVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        this.player = null;
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
    }
}