package com.example.schoolapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Activity.Youtube;
import com.example.schoolapp.Activity.videoplay;
import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

public class youtubeAdapter extends RecyclerView.Adapter<youtubeAdapter.viewHolder> {

    List<String> nameList,urlList;
    Context context;

    public youtubeAdapter(Context context, List<String> nameList, List<String> urlList) {
        this.context = context;
        this.nameList = nameList;
        this.urlList = urlList;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener =
                    new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        holder.youTubeThumbnailView.initialize(Constant.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(urlList.get(position));
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                holder.videosTitleTextView.setText(""+nameList.get(position));
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e ("error===================","fail");
            }
        });
    }


    @Override
    public int getItemCount() {
        return urlList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        protected YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        protected TextView videosTitleTextView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
            videosTitleTextView =  itemView.findViewById(R.id.videosTitle_tv);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context.getApplicationContext(), videoplay.class);
            intent.putExtra("keyvalue",urlList.get(getLayoutPosition()));
            context.startActivity(intent);
        }
    }
}
