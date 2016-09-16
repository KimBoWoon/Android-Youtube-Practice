package com.videovillage.application.mainactivity;

import com.videovillage.application.data.DataManager;

/**
 * Created by secret on 9/14/16.
 */
public class MainPresenter implements MainContract.UserAction {
    private MainContract.View mMainView;
    private MainModel mMainModel;

    public MainPresenter(MainContract.View view) {
        this.mMainView = view;
        this.mMainModel = new MainModel();
    }

    @Override
    public String choiceYoutubeChannel(String videoName) {
        DataManager.getDataManager().getVideoEntry().clear();

        return mMainModel.videoNameTrim(videoName);
    }
}
