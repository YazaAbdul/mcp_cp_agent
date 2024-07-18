package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.BhkResponse;

import java.util.ArrayList;

/**
 * Created by venkei on 21-May-19.
 */
public class BhkAdapter extends RecyclerView.Adapter<BhkAdapter.ViewHolder> {

    private Context context;
    private ArrayList<BhkResponse> mobiles = new ArrayList<BhkResponse>();
    BhkAdapter.ChildItemCLicListener childItemCLicListener;
    int row_index ;

    public BhkAdapter(Context context, ArrayList<BhkResponse> mobiles) {
        this.context = context;
        this.mobiles = mobiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bhk_list_item,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        TextView modelTextView = holder.plotNumberTVID;
        modelTextView.setText(mobiles.get(position).getBhk_type());


        final RelativeLayout plotBGID = holder.plotBGID;

        plotBGID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childItemCLicListener.childItemClickedPosition(mobiles.get(position).getBhk_type());
            }
        });


        Log.e("api==>", "color "+mobiles.get(position).getColor());
       holder.plotBGID.setBackgroundColor(Color.parseColor(mobiles.get(position).getColor()));

    }

    public void setSubCategoriesHolderslist(ArrayList<BhkResponse> subCategoriesHolderslist) {
        this.mobiles = subCategoriesHolderslist;
        notifyItemRangeChanged(0, subCategoriesHolderslist.size());
    }

    @Override
    public int getItemCount() {
        return mobiles.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView plotNumberTVID;
        RelativeLayout plotBGID;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plotNumberTVID = itemView.findViewById(R.id.plotNumberTVID);
            plotBGID = itemView.findViewById(R.id.plotBGID);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {





                }
            });*/
        }
    }

    public void setChildClickListener(ChildItemCLicListener childClickListener) {
        this.childItemCLicListener = childClickListener;
    }

    public interface ChildItemCLicListener {
        public void childItemClickedPosition(String name);
    }
}

