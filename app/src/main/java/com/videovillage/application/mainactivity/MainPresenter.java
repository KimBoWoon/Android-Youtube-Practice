package com.videovillage.application.mainactivity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.videovillage.application.R;
import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.data.Loging;
import com.videovillage.application.data.SharedStore;

import java.util.HashMap;

/**
 * Created by secret on 9/14/16.
 */
public class MainPresenter implements MainContract.UserAction {
    private MainContract.View mMainView;
    private MainModel mMainModel;
    private HashMap<String, String> youtubeSubscribeList;
    private FirebaseRemoteConfig config;
    public static boolean DEBUG = false;


    MainPresenter(MainContract.View view) {
        this.mMainView = view;
        this.mMainModel = new MainModel();
        DEBUG = isDebuggable((MainActivity) view);

        youtubeSubscribeList = new HashMap<String, String>();

        // 1. config 획득
        this.config = FirebaseRemoteConfig.getInstance();
        // 2. setting 획득
        FirebaseRemoteConfigSettings configSettings
                = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();

        // 3. 설정
        this.config.setConfigSettings(configSettings);
        // 4. data 획득
        loadRemoteData();

        checkYouTubeApi();

        youtubeChannelInsert();
    }

    @Override
    public String choiceYoutubeChannel(String videoName) {
        DataManager.getDataManager().getVideoEntry().clear();

        return mMainModel.videoNameTrim(videoName);
    }

    public boolean isDebuggable(Context context) {
        boolean debuggable = false;
        PackageManager pm = context.getPackageManager();

        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /* debuggable variable will remain false */
            e.printStackTrace();
        }

        return debuggable;
    }

    private void loadRemoteData() {
        // 리모트로 가져오는 시간
        long cacheException = 3600;
        if (config.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheException = 0;
        }

        // 원격 매개변수 요청
        config.fetch(cacheException).addOnCompleteListener((MainActivity) mMainView, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    config.activateFetched();

                    // 원격 매개변수 데이터 저장
                    // 늦게 저장이 되더라도 한번 저장되면 앱 구동시 즉각 반응 가능하다.
                    SharedStore.setInt((MainActivity) mMainView,
                            Constant.RECOVERY_DIALOG_REQUEST,
                            config.getLong(Constant.RECOVERY_DIALOG_REQUEST));
                    SharedStore.setString((MainActivity) mMainView,
                            Constant.YOUTUBE_ANDROID_API_KEY,
                            config.getString(Constant.YOUTUBE_ANDROID_API_KEY));
                    SharedStore.setString((MainActivity) mMainView,
                            Constant.YOUTUBE_SERVER_API_KEY,
                            config.getString(Constant.YOUTUBE_SERVER_API_KEY));
                    SharedStore.setString((MainActivity) mMainView,
                            Constant.VIDEO_SEARCH_URL,
                            config.getString(Constant.VIDEO_SEARCH_URL));

                    Loging.i(String.valueOf(config.getString(Constant.RECOVERY_DIALOG_REQUEST)));
                    Loging.i(String.valueOf(config.getString(Constant.YOUTUBE_ANDROID_API_KEY)));
                    Loging.i(String.valueOf(config.getString(Constant.YOUTUBE_SERVER_API_KEY)));
                    Loging.i(String.valueOf(config.getString(Constant.VIDEO_SEARCH_URL)));
                }
            }
        });
    }

    private void checkYouTubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable((MainActivity) mMainView);
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog((MainActivity) mMainView,
                    SharedStore.getInt((MainActivity) mMainView,
                            Constant.RECOVERY_DIALOG_REQUEST)).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMessage =
                    String.format(((MainActivity) mMainView)
                            .getString(R.string.error_player), errorReason.toString());
            Toast.makeText((MainActivity) mMainView, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void youtubeChannelInsert() {
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
    }

    public HashMap<String, String> getYoutubeSubscribeList() {
        return youtubeSubscribeList;
    }
}
