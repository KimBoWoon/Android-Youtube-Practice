package com.videovillage.application.mainactivity;

import android.content.Context;

/**
 * Created by secret on 9/14/16.
 */
public interface MainContract {
    interface View {
        void initView();
        void initActionbar();
        void youtubeChannelInsert();
        void onListItemClick(int position);
        boolean isDebuggable(Context context);
    }

    interface UserAction {
        String choiceYoutubeChannel(String videoName);
    }
}
