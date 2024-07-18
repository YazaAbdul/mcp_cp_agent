package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.VideosResponce;

import java.util.ArrayList;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosVH>{

    private ArrayList<VideosResponce> project_videos = new ArrayList<>();
    private Activity activity;

    public VideosAdapter (ArrayList<VideosResponce> videosArray,Activity activity){
        this.project_videos=videosArray;
        this.activity =activity;
    }

    public VideosAdapter(ArrayList<VideosResponce> project_videos) {
        this.project_videos = project_videos;
    }

    @NonNull
    @Override
    public VideosVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideosVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_videos,parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull VideosVH holder, int position) {

        WebSettings webSettings = holder.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        holder.webView.setWebViewClient(new Callback());
        Log.e("api==>", "videos url ==> "+project_videos.get(position).getVedio_url());
      //  holder.webView.loadUrl("https://www.youtube.com/embed/"+project_videos.get(position).getVedio_url());

        try {
            if(!project_videos.get(position).getVedio_url().contains("tranquilcrm.in")){
                holder.video_CV.setVisibility(View.GONE);
                holder.webView.loadUrl("https://www.youtube.com/embed/"+project_videos.get(position).getVedio_url());
                Log.e("api==>", "in inview ");
            }else{
              /*//  holder.webView.loadUrl(project_videos.get(position).getVedio_url());

                MediaController mediaController = new MediaController(activity);
                mediaController.setAnchorView(holder.videoView);
                holder.videoView.setMediaController(mediaController);

                // Set the video URI and start playback
                holder.videoView.setVideoURI(https://tranquilcrm.in/attachments/konnipati_shivantha.mp4);
                holder.videoView.start();
             //   holder.videoView.loadUrl(project_videos.get(position).getVedio_url());*/
                holder.webivew_CV.setVisibility(View.GONE);
                Uri uri = Uri.parse(project_videos.get(position).getVedio_url());
                holder.videoView.setVideoURI(uri);
                holder.videoView.requestFocus();
                holder.videoView.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class Callback extends WebViewClient{
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return project_videos.size();
    }

    public static class VideosVH extends RecyclerView.ViewHolder {

        WebView webView;
        CardView video_CV,webivew_CV;
        VideoView videoView;
        public VideosVH(@NonNull View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.web_viewID);
            video_CV = itemView.findViewById(R.id.video_CV);
            webivew_CV = itemView.findViewById(R.id.webivew_CV);
            videoView = itemView.findViewById(R.id.videoView);
        }
    }

}
