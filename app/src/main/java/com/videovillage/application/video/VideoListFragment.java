package com.videovillage.application.video;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.videovillage.application.adapter.PageAdapter;
import com.videovillage.application.data.DataManager;
import com.videovillage.application.data.VideoEntry;

/**
 * Created by secret on 8/27/16.
 */
/**
 * A fragment that shows a static list of videos.
 */
public final class VideoListFragment extends ListFragment {
    private PageAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new PageAdapter(getActivity(), DataManager.getDataManager().getVideoEntry());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        VideoEntry videoEntry = DataManager.getDataManager().getVideoEntry().get(position);

        Intent videoPlay = new Intent(getActivity(), VideoPlayActivity.class);
        videoPlay.putExtra("VideoEntry", videoEntry);
        startActivity(videoPlay);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        adapter.releaseLoaders();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setLabelVisibility(boolean visible) {
        adapter.setLabelVisibility(visible);
    }

    public PageAdapter getAdapter() {
        return adapter;
    }
}
