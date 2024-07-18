package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.OnLoadMoreListener;
import com.mcpcustomer_post_new_ps_n.android.ui.activities.sales.SubCategoriesActivity;
import com.mcpcustomer_post_new_ps_n.android.ui.models.HomeMainResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by venkei on 23-Oct-18.
 */
public class DashBoardCategoriesAdapter extends RecyclerView.Adapter {
    private static final int REQUEST_CALL = 1;
    String custid, cnumber, cemail, custanme;

    String mobileNumberStr;
    ClickListener clickListener;
    private LayoutInflater layoutInflater;
    private ArrayList<HomeMainResponse> crmactivityholders = new ArrayList<>();
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    Activity activity;

    public void setPaginationAdapter(ArrayList<HomeMainResponse> crmactivityholders) {
        this.crmactivityholders = crmactivityholders;
        notifyItemRangeChanged(0, crmactivityholders.size());
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public DashBoardCategoriesAdapter(Activity activity, RecyclerView mRecyclerView) {
        layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return crmactivityholders.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.home_list_item, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_progress, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            final HomeMainResponse user = crmactivityholders.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;

            userViewHolder.homeTittle.setText(user.getMenu_name());

            if (user.getLocation().equals("null")) {
                userViewHolder.locationTVID.setVisibility(View.GONE);

            } else {

                userViewHolder.locationTVID.setText(user.getLocation());
            }

            Picasso.get().load(user.getMenu_icon()).into(userViewHolder.homeImage);
            userViewHolder.homeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(activity, SubCategoriesActivity.class);
                    intent.putExtra("menu_id", user.getMenu_id());
                    intent.putExtra("menu_name", user.getMenu_name());
                    intent.putExtra("location_name", user.getLocation());
                    intent.putExtra("brochure", user.getBrochure_dtl());
                    intent.putExtra("layout", user.getLayout_dtl());
                    intent.putExtra("application", user.getApplication_dtl());
                    activity.startActivity(intent);
                }
            });

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemCount() {
        return crmactivityholders == null ? 0 : crmactivityholders.size();
    }

    public void setLoaded() {
        isLoading = false;
    }


    class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView homeTittle;
        private TextView locationTVID;
        private ImageView homeImage;

        public UserViewHolder(View itemView) {
            super(itemView);
            homeImage = itemView.findViewById(R.id.homeImage);
            homeTittle = itemView.findViewById(R.id.homeTittle);
            locationTVID = itemView.findViewById(R.id.locationTVID);
        }


    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }


    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
        }
    }

}

