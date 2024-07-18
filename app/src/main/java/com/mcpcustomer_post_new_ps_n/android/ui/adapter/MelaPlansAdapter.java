package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.MySharedPreferences;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.MelaPlansResponse;
import com.mcpcustomer_post_new_ps_n.android.ui.models.StatusResponseNew;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MelaPlansAdapter extends RecyclerView.Adapter<MelaPlansAdapter.MelaPlansVH> {


    ArrayList<MelaPlansResponse> melaPlansResponses = new ArrayList<>();
    Activity activity;
    MelaPlanClickListener melaPlanClickListener;


    public MelaPlansAdapter(ArrayList<MelaPlansResponse> melaPlansResponses, Activity activity,MelaPlanClickListener melaPlanClickListener) {
        this.melaPlansResponses = melaPlansResponses;
        this.activity = activity;
        this.melaPlanClickListener = melaPlanClickListener;
    }

    @NonNull
    @Override
    public MelaPlansVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MelaPlansVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.mela_plans_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MelaPlansVH holder, int position) {

        MelaPlansResponse item = melaPlansResponses.get(position);

        try {
            holder.associateCountTVID.setText(melaPlansResponses.get(position).getAssociate_count());
            holder.customersCountTVID.setText(melaPlansResponses.get(position).getCustomer_count());
            holder.dateTVID.setText(melaPlansResponses.get(position).getCreated_date());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.melaPlanDeleteRLID.setOnClickListener(v -> melaPlanClickListener.onMelaPlanItemClick(item, v, position, holder));
        holder.melaUpdateLLID.setOnClickListener(v -> melaPlanClickListener.onMelaPlanItemClick(item, v, position, holder));

        /*holder.deleteRLID.setOnClickListener(v -> {
            deleteMela(melaPlansResponses.get(position).getS_no(),holder.pb);

        });*/

    }

    private void deleteMela(String S_no, ProgressBar pb) {

        pb.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StatusResponseNew> call = apiInterface.getMelaDelete(S_no);
        Log.e("api==>",call.request().toString());
        call.enqueue(new Callback<StatusResponseNew>() {
            @Override
            public void onResponse(Call<StatusResponseNew> call, Response<StatusResponseNew> response) {
                pb.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200){

                    StatusResponseNew statusResponseNew = response.body();

                    if (statusResponseNew.getStatus().equalsIgnoreCase("1")){
                        Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                        MySharedPreferences.setPreference(activity,"REFRESH","YES");
                        //loadMelaUpdate();
                    }else {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<StatusResponseNew> call, Throwable t) {
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public int getItemCount() {
        return melaPlansResponses.size();
    }

    public class  MelaPlansVH extends RecyclerView.ViewHolder{

        AppCompatTextView associateCountTVID,customersCountTVID,dateTVID;
        RelativeLayout melaPlanDeleteRLID;
        ProgressBar pb;
        LinearLayout melaUpdateLLID;

        public MelaPlansVH(@NonNull View itemView) {
            super(itemView);

            associateCountTVID = itemView.findViewById(R.id.associateCountTVID);
            customersCountTVID = itemView.findViewById(R.id.customersCountTVID);
            dateTVID = itemView.findViewById(R.id.dateTVID);
            melaPlanDeleteRLID = itemView.findViewById(R.id.melaPlanDeleteRLID);
            pb = itemView.findViewById(R.id.pb);
            melaUpdateLLID = itemView.findViewById(R.id.melaUpdateLLID);
        }
    }

    public interface MelaPlanClickListener {
        void onMelaPlanItemClick(MelaPlansResponse response, View v, int pos, MelaPlansVH holder);
    }
}
