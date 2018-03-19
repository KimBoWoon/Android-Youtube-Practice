package com.dabang.byul.byuldabang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dabang.byul.byuldabang.R;
import com.dabang.byul.byuldabang.data.Constant;
import com.dabang.byul.byuldabang.model.VideosList;
import com.dabang.byul.byuldabang.video.VideoPlayActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Null on 2018-03-19.
 */

interface ItemClickListener {
    void onItemClick(int position);
}

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemClickListener {
    private List<VideosList> items;
    private Context context;

    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private final ThumbnailListener thumbnailListener;

    private boolean labelsVisible;

    public RecyclerAdapter(Context context, List<VideosList> items) {
        this.context = context;
        this.items = items;
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        thumbnailListener = new ThumbnailListener();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        holder = new VideoHolder(v, this);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        HashMap<String, Object> snippet = items.get(0).getItems().get(position).getSnippet();
        HashMap<String, Object> id = items.get(0).getItems().get(position).getId();
        ((VideoHolder) holder).videoTitle.setText(snippet.get("title").toString());
        ((VideoHolder) holder).thumbnail.setTag(id.get("videoId"));
        ((VideoHolder) holder).thumbnail.initialize(Constant.YOUTUBE_API_KEY, thumbnailListener);
    }

    @Override
    public int getItemCount() {
        return items.get(0).getItems().size();
    }

    @Override
    public void onItemClick(int position) {
        Intent videoPlay = new Intent(context, VideoPlayActivity.class);
        videoPlay.putExtra("VideoEntry", items.get(0).getItems().get(position));
        videoPlay.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(videoPlay);
    }

    public String getItemTitle(int position) {
        return items.get(position).getItems().get(position).getSnippet().get("title").toString();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        public TextView videoTitle;
        public YouTubeThumbnailView thumbnail;

        public VideoHolder(View itemView, final ItemClickListener itemClickListener) {
            super(itemView);
            this.videoTitle = (TextView) itemView.findViewById(R.id.video_title);
            this.thumbnail = (YouTubeThumbnailView) itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            thumbnailViewToLoaderMap.put(view, loader);
            view.setImageResource(R.drawable.loading_thumbnail);
            String videoId = (String) view.getTag();
            loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
            view.setImageResource(R.drawable.no_thumbnail);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.drawable.no_thumbnail);
        }
    }
}