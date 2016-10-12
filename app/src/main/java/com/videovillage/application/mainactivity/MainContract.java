package com.videovillage.application.mainactivity;

import java.util.HashMap;

/**
 * Created by secret on 9/14/16.
 */
public interface MainContract {
    interface View {
        void initView();
        void initActionbar();
        void onListItemClick(int position);
    }

    interface UserAction {
        String choiceYoutubeChannel(String videoName);
        HashMap<String, String> getYoutubeSubscribeList();
    }
}
