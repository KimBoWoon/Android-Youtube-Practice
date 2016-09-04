package com.videovillage.application.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.videovillage.application.R;
import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.thread.DataGetThread;
import com.videovillage.application.video.VideoListFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A sample Activity showing how to manage multiple YouTubeThumbnailViews in an adapter for display
 * in a List. When the list items are clicked, the video is played by using a YouTubePlayerFragment.
 * <p>
 * The demo supports custom fullscreen and transitioning between portrait and landscape without
 * rebuffering.
 */

public class MainActivity extends Activity implements OnFullscreenListener {
    private VideoListFragment listFragment;
    private AsyncTask<String, Integer, String> task;
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TypefaceProvider.registerDefaultIconSets();
        ButterKnife.bind(this);

        listFragment = (VideoListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);

        aq = new AQuery(this);

        checkYouTubeApi();
    }

    @OnClick(R.id.video_name_submit)
    public void videoSearch() {
        String videoName = aq.id(R.id.video_name).getEditText().getText().toString();

        if (videoName.length() == 0)
            Toast.makeText(getApplicationContext(), "검색어를 입력해주세요!", Toast.LENGTH_SHORT).show();
        else {
            videoName = videoName.replace(" ", "%20");
            DataManager.getDataManager().getVideoEntry().clear();
            task = new DataGetThread(MainActivity.this, listFragment);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, videoName);
        }
    }

    private void checkYouTubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, Constant.RECOVERY_DIALOG_REQUEST).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMessage =
                    String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.RECOVERY_DIALOG_REQUEST) {
            // Recreate the activity if user performed a recovery action
            recreate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
//        this.isFullscreen = isFullscreen;
    }
}