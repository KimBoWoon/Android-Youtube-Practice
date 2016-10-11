package com.videovillage.application.http;

/**
 * Created by secret on 9/14/16.
 */
public interface HttpContract {
    interface View {

    }

    interface UserAction {
        void initHttpConnection(String urlString);
        String getJSONString();
    }
}
