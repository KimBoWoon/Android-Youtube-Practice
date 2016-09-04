package com.videovillage.application.mainactivity;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A sample Activity showing how to manage multiple YouTubeThumbnailViews in an adapter for display
 * in a List. When the list items are clicked, the video is played by using a YouTubePlayerFragment.
 * <p/>
 * The demo supports custom fullscreen and transitioning between portrait and landscape without
 * rebuffering.
 */

public class MainActivity extends AppCompatActivity implements OnFullscreenListener {
    private VideoListFragment listFragment;
    private AsyncTask<String, Integer, String> task;
    private AQuery aq;
    private String[] navItems = {"망가녀 Mangganyeo", "남욱이의 웃기는 일상", "HANA 김하나", "귄펭 GUINPEN",
            "가랏 혜수몬", "맹채연구소", "안재억", "0zoo 영주", "진이 유튜브", "채르니 Chaerny", "공대생 변승주",
            "여정을 떠난 여정", "공대언니 Engin2ring_girl", "예쁘린", "신별 ShinByul", "이루리 ILULIY",
            "비디오빌리지", "걸스빌리지", "보이즈빌리지"};
    private ListView lvNavList;
    private LinearLayout flContainer;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TypefaceProvider.registerDefaultIconSets();
        ButterKnife.bind(this);

        listFragment = (VideoListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);

        aq = new AQuery(this);

        lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);
        flContainer = (LinearLayout) findViewById(R.id.main_content);
        Arrays.sort(navItems);

        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());

        dlDrawer = (DrawerLayout) findViewById(R.id.container);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        dlDrawer.setDrawerListener(dtToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkYouTubeApi();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            String videoName = navItems[position];

            videoName = videoName.replace(" ", "%20");
            DataManager.getDataManager().getVideoEntry().clear();
            task = new DataGetThread(MainActivity.this, listFragment);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, videoName);

            dlDrawer.closeDrawer(lvNavList);
        }
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return dtToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
//        this.isFullscreen = isFullscreen;
    }
}