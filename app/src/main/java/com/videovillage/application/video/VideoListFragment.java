package com.videovillage.application.video;

import android.app.ListFragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.videovillage.application.R;
import com.videovillage.application.adapter.PageAdapter;
import com.videovillage.application.constant.Constant;
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
    private View videoBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        DataManager.getDataManager().getVideoEntry().add(new VideoEntry("[7/15/30] 일주일 동안 핸드폰 없이 살기 * 화난 범PD 촬영 도중 욕설 내뱉다* (feat.범PD) [보이즈빌리지]", "bzTjB3LuR0U"));
//        DataManager.getDataManager().getVideoEntry().add(new VideoEntry("[7/15/30] 19+ \"남자는 원데이 원X\" 남자들이 15일동안 야동을 안 봤을 때 (feat.범PD) [보이즈빌리지]", "sbSwCfDaBko"));
//        DataManager.getDataManager().getVideoEntry().add(new VideoEntry("[해석남녀] 남녀가 19금 방송불가 판정받은 뮤직비디오를 같이 본다면?! (feat. 김PD, 균반장, 빈PD, 주, 엘, 큐,) [걸스빌리지]", "ZzJmeDCcIMw"));
//        DataManager.getDataManager().getVideoEntry().add(new VideoEntry("[김남욱] 비빌라이프 1화 (비빌라이프는 멜론이다!) (feat. 김하나) [남욱이의 비빌라이프]", "qYCjg5wcbv4"));
//        DataManager.getDataManager().getVideoEntry().add(new VideoEntry("[김남욱] 비빌라이프 2화 (비빌라이프는 웃음대잔치다!) (feat. 망가녀, 김하나, 신별, 예쁘린, 귄펭) [남욱이의 비빌라이프]", "Rm7MbYy6r0Y"));
//        DataManager.getDataManager().getVideoEntry().add(new VideoEntry("[김남욱] 남욱이 실명위기!? 춤추다가 눈탱이 박살나서 실명할 뻔한 비빌라이프 3화 [남욱이의 비빌라이프]", "7wp8xwDGeaQ"));
//        DataManager.getDataManager().getVideoEntry().add(new VideoEntry("[김남욱] 공대생 머리 박살?! 머리로 수박을 깨버리는 요것이 바로 비빌라이프 4화 [남욱이의 비빌라이프]", "10UjpEeY5x0"));
//        DataManager.getDataManager().getVideoEntry().add(new VideoEntry("[김남욱] 더위에 미쳐버린 요것이 바로 비빌라이프 5탄!! [남욱이의 비빌라이프]", "YMduQGPZqrc"));

        adapter = new PageAdapter(getActivity(), DataManager.getDataManager().getVideoEntry());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        videoBox = getActivity().findViewById(R.id.video_box);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String videoId = DataManager.getDataManager().getVideoEntry().get(position).getVideoId();

        VideoFragment videoFragment =
                (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);
        videoFragment.setVideoId(videoId);

        // The videoBox is INVISIBLE if no video was previously selected, so we need to show it now.
        if (videoBox.getVisibility() != View.VISIBLE) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                // Initially translate off the screen so that it can be animated in from below.
                videoBox.setTranslationY(videoBox.getHeight());
            }
            videoBox.setVisibility(View.VISIBLE);
        }

        // If the fragment is off the screen, we animate it in.
        if (videoBox.getTranslationY() > 0) {
            videoBox.animate().translationY(0).setDuration(Constant.ANIMATION_DURATION_MILLIS);
            Toast.makeText(getActivity(), "VideoBox", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        adapter.releaseLoaders();
    }

    public void setLabelVisibility(boolean visible) {
        adapter.setLabelVisibility(visible);
    }

    public PageAdapter getAdapter() {
        return adapter;
    }
}
