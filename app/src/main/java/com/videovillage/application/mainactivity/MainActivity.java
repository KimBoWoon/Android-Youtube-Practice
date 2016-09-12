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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.videovillage.application.R;
import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.thread.VideosSearchThread;
import com.videovillage.application.video.VideoListFragment;

import java.util.Arrays;
import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * A sample Activity showing how to manage multiple YouTubeThumbnailViews in an adapter for display
 * in a List. When the list items are clicked, the video is played by using a YouTubePlayerFragment.
 * <p/>
 * The demo supports custom fullscreen and transitioning between portrait and landscape without
 * rebuffering.
 */

public class MainActivity extends AppCompatActivity {
    private VideoListFragment listFragment;
    private AsyncTask<String, Integer, String> task;
    private AQuery aq;
    private HashMap<String, String> youtubeSubscribeList = new HashMap<String, String>();
    private String[] navItems = {"망가녀 Mangganyeo", "남욱이의 욱기는 일상", "HANA 김하나", "귄펭 GUINPEN",
            "가랏 혜수몬", "맹채연구소", "안재억", "0zoo 영주", "진이 유튜브", "채르니 Chaerny", "공대생 변승주",
            "여정을 떠난 여정", "공대언니 Engin2ring_girl", "예쁘린", "신별 ShinByul", "이루리 ILULIY",
            "비디오빌리지", "걸스빌리지", "보이즈빌리지", "욱망나생", "이채은 CHAEEUN", "재인 아카데미 (Jaein Academy)",
            "큐피디", "엘피디", "김피디", "범피디", "정아TV", "JK ENT", "주", "BEAN", "유소 (YUSO)", "Hemtube (햄튜브)"};
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

        aq = new AQuery(this);

        youtubeSubscribeList.put("망가녀 Mangganyeo", "UCTCYh96Ex4lRWMRg0YQvlIQ");
        youtubeSubscribeList.put("남욱이의 욱기는 일상", "UCIU2ghnE-3MMhLTlh_7hzZQ");
        youtubeSubscribeList.put("HANA 김하나", "UCfTswP_uNy_h86pUjCU410A");
        youtubeSubscribeList.put("귄펭 GUINPEN", "UCkkfPjfgr8LZLrifl3FmNhg");
        youtubeSubscribeList.put("가랏 혜수몬", "UCUlykKBn12YNqb2U2rnogGQ");
        youtubeSubscribeList.put("맹채연구소", "UCEwq4lvvcNHdVWmpW9Je2SQ");
        youtubeSubscribeList.put("안재억", "UC5xBoynZ_GvifP3J0Le12yw");
        youtubeSubscribeList.put("0zoo 영주", "UC7buwq_navFjilxdYaGO0xw");
        youtubeSubscribeList.put("진이 유튜브", "UCIHKbd4QntJvCXQqvRrBp6Q");
        youtubeSubscribeList.put("채르니 Chaerny", "UCZVpc4jDiGdfmJigYivj6pw");
        youtubeSubscribeList.put("공대생 변승주", "UChE5nZAIhWS5vYTRjsUgRpQ");
        youtubeSubscribeList.put("여정을 떠난 여정", "UCsU2RlGgybcLzfmBqnTx-Rw");
        youtubeSubscribeList.put("공대언니 Engin2ring_girl", "UC5R_lLymIFhoFQlqLgZsNqw");
        youtubeSubscribeList.put("예쁘린", "UCGViywDjm32R0UuyaBVE7pQ");
        youtubeSubscribeList.put("신별 ShinByul", "UCUcIUXAGcXpnu8gH1HKL6Tw");
        youtubeSubscribeList.put("이루리 ILULIY", "UCG0JecZ4QvGGfO-asHGhCIA");
        youtubeSubscribeList.put("욱망나생", "UCd2D8vMvf3MPt6T3sncSfeA");
        youtubeSubscribeList.put("이채은 CHAEEUN", "UCk4ZCDRb2lEvh5by4ceGDDw");
        youtubeSubscribeList.put("재인 아카데미 (Jaein Academy)", "UCt-O6YfZqyNkToaS7JqWxkQ");
        youtubeSubscribeList.put("큐피디", "UCV1whfczLoVM5HBgzmuknkg");
        youtubeSubscribeList.put("엘피디", "UCmncNwwVVXvTH4oYIxODxMQ");
        youtubeSubscribeList.put("김피디", "UCPganpI_QsoOiA6-J0C-X-Q");
        youtubeSubscribeList.put("범피디", "UCOpWGjq4Yp7izNRfShkekjw");
        youtubeSubscribeList.put("정아TV", "UCXoyOGFkB1ssgOf7ZjCqQlQ");
        youtubeSubscribeList.put("JK ENT", "UCIB_oNqi62rKnPFb3Toaozw");
        youtubeSubscribeList.put("주", "UCG_1467YPgA4L_EywJKisXw");
        youtubeSubscribeList.put("BEAN", "UCDgz8c2bv-VaTbraQQFdpjQ");
        youtubeSubscribeList.put("유소 (YUSO)", "UCGRxkFCoi0RsWelthcaTjMw");
        youtubeSubscribeList.put("Hemtube (햄튜브)", "UCPydsWBQpnXGICE-XWGNdow");
        youtubeSubscribeList.put("비디오빌리지", "UCENi3E1IyV0nvIIrha8GIvQ");
        youtubeSubscribeList.put("걸스빌리지", "UCedoBeDMzwnPJazRvvoOXhQ");
        youtubeSubscribeList.put("보이즈빌리지", "UCuSaFvVbK9QpZlbn8Vf34RA");

        listFragment = (VideoListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);

        lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);
//        flContainer = (LinearLayout) findViewById(R.id.main_content);
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
//            String videoName = navItems[position];
            String videoName = youtubeSubscribeList.get(navItems[position]);

            videoName = videoName.replace(" ", "%20");
            DataManager.getDataManager().getVideoEntry().clear();
            task = new VideosSearchThread(MainActivity.this, listFragment);
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

//    @OnClick(R.id.video_name_submit)
//    public void videoSearch() {
//        String videoName = aq.id(R.id.video_name).getEditText().getTitle().toString();
//
//        if (videoName.length() == 0)
//            Toast.makeText(getApplicationContext(), "검색어를 입력해주세요!", Toast.LENGTH_SHORT).show();
//        else {
//            videoName = videoName.replace(" ", "%20");
//            DataManager.getDataManager().getVideoEntry().clear();
//            task = new VideosSearchThread(MainActivity.this, listFragment);
//            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, videoName);
//        }
//    }

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
}