package com.videovillage.application.mainactivity;

/**
 * Created by secret on 9/14/16.
 */
public interface MainContract {
    interface View {
        void initView();
        void initActionbar();
        void youtubeChannelInsert();
        void onListItemClick(int position);
    }

    interface UserAction {
        String choiceYoutubeChannel(String videoName);
    }
}
