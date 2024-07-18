package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.AppConstants;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.ui.fragments.FragmentCreatives;
import com.mcpcustomer_post_new_ps_n.android.ui.models.CreativesResponse;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreativesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int VIEW_ITEM = 1;
    private static final int VIEW_LOADING = 0;
    public static Object CreativesVH;
    private boolean isLoadingAdded = false;
    FragmentCreatives activity;
    ArrayList<CreativesResponse> creativesResponses = new ArrayList<>();
    CreativeClickListener creativeClickListener;
    String cpPicStr,cpUserNameStr,cpMobileStr,cpEmailStr;

    public CreativesAdapter(FragmentCreatives activity, ArrayList<CreativesResponse> creativesResponses, CreativeClickListener creativeClickListener, String cpPicStr) {
        this.activity = activity;
        this.creativesResponses = creativesResponses;
        this.creativeClickListener = creativeClickListener;
        this.cpPicStr = cpPicStr;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (i) {
            case VIEW_ITEM:
                viewHolder = new CreativesVH(inflater.inflate(R.layout.creatives_recycler_item, parent, false));
                break;

            case VIEW_LOADING:
                viewHolder = new LoadingViewHolder(inflater.inflate(R.layout.progressbar1, parent, false));
                break;
        }

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        switch (getItemViewType(i)) {
            case VIEW_ITEM:
                CreativesVH itemVH = (CreativesVH) holder;
                CreativesResponse item = creativesResponses.get(i);

                Picasso.get().load(item.getImage()).into(itemVH.creativeIVID);

                cpUserNameStr = MySharedPreferences.getPreferences(activity.getActivity(), "" + AppConstants.CP_USER_NAME);
                cpMobileStr = MySharedPreferences.getPreferences(activity.getActivity(), "" + AppConstants.CP_USER_NUMBER);
                cpEmailStr = MySharedPreferences.getPreferences(activity.getActivity(), "" + AppConstants.CP_USER_EMAIL);
                try {
                    Picasso.get().load(cpPicStr).placeholder(R.drawable.userpic).into(itemVH.agentIVID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Picasso.get().load(R.drawable.crmappicon).into(itemVH.companyLogoIVID);
                //Picasso.get().load(R.drawable.crmappicon).into(itemVH.companyLogoIVID);

                Log.e("number==>",cpMobileStr);
                Log.e("mail==>",cpEmailStr);
                itemVH.agentNameTVID.setText(cpUserNameStr);
                itemVH.agentMobileTVID.setText(cpMobileStr);
                itemVH.agentEmailTVID.setText(cpEmailStr);

                itemVH.creativeShareBtn.setOnClickListener(v -> creativeClickListener.onCreativeItemClick(item,v,i,itemVH));
                itemVH.whatsAppShareIVID.setOnClickListener(v -> creativeClickListener.onCreativeItemClick(item,v,i,itemVH));
                itemVH.facebookShareIVID.setOnClickListener(v -> creativeClickListener.onCreativeItemClick(item,v,i,itemVH));
                itemVH.instagramShareIVID.setOnClickListener(v -> creativeClickListener.onCreativeItemClick(item,v,i,itemVH));
                itemVH.twitterShareIVID.setOnClickListener(v -> creativeClickListener.onCreativeItemClick(item,v,i,itemVH));
                itemVH.businessWhatsAppRLID.setOnClickListener(v -> creativeClickListener.onCreativeItemClick(item,v,i,itemVH));
                itemVH.agentDetailsRLID.setVisibility(View.VISIBLE);


                if(!creativesResponses.get(i).getVideo_url().isEmpty()){


                itemVH.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                        super.onReady(youTubePlayer);

                        //  youTubePlayer.loadVideo(project_videos.get(position).getVedio_url(), 0);

                        //   String videoId = extractVideoId(project_videos.get(position).getVedio_url());



                        String videoId = extractYTId(creativesResponses.get(i).getVideo_url());
                        Log.e("api==>","video==>"+creativesResponses.get(i).getVideo_url());


                        try {
                            youTubePlayer.loadVideo(videoId,0);
                            youTubePlayer.pause();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.e("api==>","video==>"+creativesResponses.get(i).getVideo_url());


                    }

                    @Override
                    public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState state) {
                        super.onStateChange(youTubePlayer, state);
                    }
                });

                }else{

                    itemVH.video_cv.setVisibility(View.GONE);

                }

                break;

            case VIEW_LOADING:
                LoadingViewHolder loadingVH = (LoadingViewHolder) holder;
                loadingVH.progressBar1.setVisibility(View.VISIBLE);
                break;
        }

    }

    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        Log.e("api==>","video==>"+vId);
        return vId;
    }


    @Override
    public int getItemCount() {

        return creativesResponses == null ? 0 : creativesResponses.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == creativesResponses.size() - 1 && isLoadingAdded) ? VIEW_LOADING : VIEW_ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new CreativesResponse());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = creativesResponses.size() - 1;
        CreativesResponse result = getItem(position);
        if (result != null) {
            creativesResponses.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(CreativesResponse movie) {
        creativesResponses.add(movie);
        notifyItemInserted(creativesResponses.size() - 1);
    }

    public void addAll(List<CreativesResponse> moveResults) {
        for (CreativesResponse result : moveResults) {
            add(result);
        }
    }


    public CreativesResponse getItem(int position) {
        return creativesResponses.get(position);
    }

    public class CreativesVH extends RecyclerView.ViewHolder{

        AppCompatImageView creativeIVID,companyLogoIVID,agentIVID;
        AppCompatTextView agentNameTVID,agentMobileTVID,agentEmailTVID;
        public AppCompatButton creativeShareBtn;
        YouTubePlayerView youTubePlayerView;
        public RelativeLayout creativeMainRLID,whatsAppShareIVID,facebookShareIVID,instagramShareIVID,
                twitterShareIVID,businessWhatsAppRLID,agentDetailsRLID;

        CardView video_cv;


        public CreativesVH(@NonNull View itemView) {
            super(itemView);

            creativeIVID = itemView.findViewById(R.id.creativeIVID);
            agentIVID = itemView.findViewById(R.id.agentIVID);
            companyLogoIVID = itemView.findViewById(R.id.companyLogoIVID);
            agentNameTVID = itemView.findViewById(R.id.agentNameTVID);
            creativeShareBtn = itemView.findViewById(R.id.creativeShareBtn);
            creativeMainRLID = itemView.findViewById(R.id.creativeMainRLID);
            agentMobileTVID = itemView.findViewById(R.id.agentMobileTVID);
            agentEmailTVID = itemView.findViewById(R.id.agentEmailTVID);
            whatsAppShareIVID = itemView.findViewById(R.id.whatsAppShareIVID);
            facebookShareIVID = itemView.findViewById(R.id.facebookShareIVID);
            instagramShareIVID = itemView.findViewById(R.id.instagramShareIVID);
            twitterShareIVID = itemView.findViewById(R.id.twitterShareIVID);
            businessWhatsAppRLID = itemView.findViewById(R.id.businessWhatsAppRLID);
            agentDetailsRLID = itemView.findViewById(R.id.agentDetailsRLID);
            youTubePlayerView = itemView.findViewById(R.id.youTubePlayerView);
            video_cv = itemView.findViewById(R.id.video_cv);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar1;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar1 = itemView.findViewById(R.id.progressBar1);
        }
    }

    public interface CreativeClickListener {
        void onCreativeItemClick(CreativesResponse response, View v, int pos, CreativesVH holder);
    }
}
