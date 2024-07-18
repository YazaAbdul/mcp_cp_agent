package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashboardChildResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by venkei on 19-Mar-19.
 */
public class DashBoardChildAdapter extends RecyclerView.Adapter<DashBoardChildAdapter.DashBoardVH> {

    private Context context;
    private List<DashboardChildResponse> mobiles = new ArrayList<DashboardChildResponse>();
    private DashBoardChildAdapter.ChildItemCLicListener childItemCLicListener;

    private String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7", "#2196F3"};
    private int[] activityIcons = {R.drawable.menu_sale, R.drawable.menu_site_calls, R.drawable.menu_calls, R.drawable.menu_nego, R.drawable.menu_sale};


    public DashBoardChildAdapter(Context context, List<DashboardChildResponse> mobiles) {
        this.context = context;
        this.mobiles = mobiles;
    }

    @NonNull
    @Override
    public DashBoardVH onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new DashBoardVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.cp_dash_board_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardVH holder, final int i) {
        holder.activityCountTVID.setText(mobiles.get(i).getCount());
        holder.activityNameTVID.setText(mobiles.get(i).getActvty_typ_nm());
        Picasso.get().load(activityIcons[i % 5]).into(holder.activityIconID);
        holder.activityIconID.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        holder.activityLLID.setCardBackgroundColor(Color.parseColor(colors[i % 5]));

        /*holder.activityLLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, LeadsViewActivity.class);
                intent.putExtra("URL", ""+mobiles.get(i).getActvty_typ_id());
                intent.putExtra("Tittle", ""+mobiles.get(i).getActvty_typ_nm());
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mobiles.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class DashBoardVH extends RecyclerView.ViewHolder {

        AppCompatImageView activityIconID;
        AppCompatTextView activityCountTVID, activityNameTVID;
        CardView activityLLID;

        public DashBoardVH(@NonNull View itemView) {
            super(itemView);
            activityIconID = itemView.findViewById(R.id.activityIconID);
            activityCountTVID = itemView.findViewById(R.id.activityCountTVID);
            activityNameTVID = itemView.findViewById(R.id.activityNameTVID);
            activityLLID = itemView.findViewById(R.id.activityLLID);
        }
    }

    public void setChildClickListener(ChildItemCLicListener childClickListener) {
        this.childItemCLicListener = childClickListener;
    }

    public interface ChildItemCLicListener {
        public void childItemClickedPosition(String name, String id, String count, int position);
    }
}

