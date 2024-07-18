package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.YoutubeVideoResponse;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{

    Activity activity;
    ArrayList<YoutubeVideoResponse> youtubeVideoResponselist;


    public VideoAdapter(ArrayList<YoutubeVideoResponse> youtubeVideoResponselist,Activity activity) {
        this.activity=activity;
        this.youtubeVideoResponselist = youtubeVideoResponselist;
    }
    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.video_list_item,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        String myYouTubeVideoUrl="https://www.youtube.com/embed/"+youtubeVideoResponselist.get(0).getImage();
        String dataUrl =
                "<html>" +
                        "<body>" +
                        "<iframe width=\"100%\" height=\"100%\" src=\""+myYouTubeVideoUrl+"\" frameborder=\"0\" allowfullscreen/>" +
                        "</body>" +
                        "</html>";

        holder.videoweb.loadData( dataUrl, "text/html" , "utf-8" );
    }

    @Override
    public int getItemCount() {
        return youtubeVideoResponselist.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        WebView videoweb;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoweb=itemView.findViewById(R.id.videoWebView);
            videoweb.getSettings().setJavaScriptEnabled(true);
            videoweb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            videoweb.getSettings().setLoadWithOverviewMode(true);
            videoweb.getSettings().setUseWideViewPort(true);
            videoweb.setWebChromeClient(new WebChromeClient(){
            });
        }
    }
}
