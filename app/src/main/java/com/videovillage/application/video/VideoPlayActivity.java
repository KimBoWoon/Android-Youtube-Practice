package com.videovillage.application.video;

import android.content.Intent;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.videovillage.application.R;
import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.data.VideoEntry;
import com.videovillage.application.thread.VideoSearchThread;

/**
 * Created by secret on 9/4/16.
 */
public class VideoPlayActivity extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {
    private YouTubePlayer player;
    private YouTubePlayerView playerView;
    private String videoId;
    private boolean isFullscreen;
    private AQuery aq = new AQuery(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_layout);

        Intent intent = getIntent();
        VideoEntry videoEntry = (VideoEntry) intent.getSerializableExtra("VideoEntry");

        playerView = (YouTubePlayerView) findViewById(R.id.video_player_youtube_player);
        playerView.initialize(Constant.YOUTUBE_ANDROID_API_KEY, this);
        setVideoId(videoEntry.getVideoId());

        VideoSearchThread videoSearchThread = new VideoSearchThread(videoEntry.getVideoId());

        aq.id(R.id.video_description_text).text(DataManager.getDataManager().getVideoInfo().getDescription());
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

    public void pause() {
        if (player != null) {
            player.pause();
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