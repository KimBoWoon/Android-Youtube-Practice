package com.videovillage.application.mainactivity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.videovillage.application.constant.Constant;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.data.SharedStore;

/**
 * Created by secret on 9/14/16.
 */
public class MainPresenter implements MainContract.UserAction {
    private MainContract.View mMainView;
    private MainModel mMainModel;
    private FirebaseRemoteConfig config;

    MainPresenter(MainContract.View view) {
        this.mMainView = view;
        this.mMainModel = new MainModel();

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
    }

    @Override
    public String choiceYoutubeChannel(String videoName) {
        DataManager.getDataManager().getVideoEntry().clear();

        return mMainModel.videoNameTrim(videoName);
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

                    Log.i("FCM", String.valueOf(config.getString(Constant.RECOVERY_DIALOG_REQUEST)));
                    Log.i("FCM", String.valueOf(config.getString(Constant.YOUTUBE_ANDROID_API_KEY)));
                    Log.i("FCM", String.valueOf(config.getString(Constant.YOUTUBE_SERVER_API_KEY)));
                    Log.i("FCM", String.valueOf(config.getString(Constant.VIDEO_SEARCH_URL)));
                }
            }
        });
    }
}
