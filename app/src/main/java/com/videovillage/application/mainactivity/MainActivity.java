package com.videovillage.application.mainactivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.videovillage.application.R;
import com.videovillage.application.adapter.PageAdapter;
import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.data.SharedStore;
import com.videovillage.application.data.VideoEntry;
import com.videovillage.application.thread.VideosSearchThread;
import com.videovillage.application.video.VideoPlayActivity;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * A sample Activity showing how to manage multiple YouTubeThumbnailViews in an adapter for display
 * in a List. When the list items are clicked, the video is played by using a YouTubePlayerFragment.
 * <p/>
 * The demo supports custom fullscreen and transitioning between portrait and landscape without
 * rebuffering.
 */

public class MainActivity extends AppCompatActivity implements MainContract.View, ListView.OnItemClickListener {
    private AQuery aq;
    private String[] navItems;
    private ListView lvNavList;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;
    private MainContract.UserAction mMainPresenter;
    private ListView listView;
    private PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initActionbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return dtToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SharedStore.getInt(MainActivity.this, Constant.RECOVERY_DIALOG_REQUEST)) {
            recreate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        String videoName = mMainPresenter.getYoutubeSubscribeList().get(navItems[position]);

        videoName = mMainPresenter.choiceYoutubeChannel(videoName);
        new VideosSearchThread(MainActivity.this, pageAdapter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, videoName);

        dlDrawer.closeDrawer(lvNavList);
    }

    @Override
    @OnItemClick(R.id.listview)
    public void onListItemClick(int position) {
        VideoEntry videoEntry = DataManager.getDataManager().getVideoEntry().get(position);

        Intent videoPlay = new Intent(getApplicationContext(), VideoPlayActivity.class);
        videoPlay.putExtra("VideoEntry", videoEntry);
        startActivity(videoPlay);
    }

    @Override
    public void initView() {
        mMainPresenter = new MainPresenter(this);

        TypefaceProvider.registerDefaultIconSets();
        ButterKnife.bind(this);

        aq = new AQuery(this);

        navItems = new String[]{"망가녀 Mangganyeo", "남욱이의 욱기는 일상", "HANA 김하나", "귄펭 GUINPEN",
                "가랏 혜수몬", "맹채연구소", "안재억", "0zoo 영주", "진이 유튜브", "채르니 Chaerny", "공대생 변승주",
                "여정을 떠난 여정", "공대언니 Engin2ring_girl", "예쁘린", "신별 ShinByul", "이루리 ILULIY",
                "비디오빌리지", "걸스빌리지", "보이즈빌리지", "욱망나생", "이채은 CHAEEUN", "재인 아카데미 (Jaein Academy)",
                "큐피디", "엘피디", "김피디", "범피디", "정아TV", "JK ENT", "주", "BEAN", "유소 (YUSO)", "Hemtube (햄튜브)"};

        listView = (ListView) findViewById(R.id.listview);
        pageAdapter = new PageAdapter(MainActivity.this, DataManager.getDataManager().getVideoEntry());
        listView.setAdapter(pageAdapter);

        lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);
        Arrays.sort(navItems);

        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(this);
    }

    @Override
    public void initActionbar() {
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
    }
}