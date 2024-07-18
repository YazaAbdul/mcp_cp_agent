package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;


import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.RequirementResponse;

import java.util.ArrayList;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.MyviewHolder> {

        Context context;
        ArrayList<RequirementResponse> data;
        ServiceClickListener click;

        public ProjectsAdapter(Context context, ArrayList<RequirementResponse> data) {
                this.context = context;
                this.data = (ArrayList<RequirementResponse>) data;
                }
        // public int[] mColors = {(R.drawable.red), (R.drawable.yellow), (R.drawable.blue)};

        public void setClick(ServiceClickListener click){
                this.click=click;
        }


        @Override
        public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.projects_adapter_list_item, parent, false);

                return new MyviewHolder(view);
                }

        @Override
        public void onBindViewHolder(MyviewHolder holder, int position) {

                String AppliedDetails;
                String program_name;
                String program_category;
                String program_id;
                RequirementResponse current = data.get(position);
                program_name = current.getProject_name();
                program_id = current.getProject_id();
                System.out.println("//datasize1" + current.getProject_id());
                holder.checkBox.setText(current.getProject_name());
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked) {
                                        click.onAdd(current);
                                } else{
                                        click.onRemove(current);
                                }
                        }
                });

                holder.checkBox.isChecked();
                //  holder.imge.setBackgroundColor(Color.parseColor(String.valueOf(mColors[position % 3])));

                }

        @Override
        public int getItemCount() {
                System.out.println("//datasize" + data.size());
                return data.size();


      /*  if (data != null) {
            return data.size();
        }
        return 0;*/
        }
/*  return 0;*/


        public static class MyviewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            public MyviewHolder(View v) {
                super(v);
                checkBox = v.findViewById(R.id.checkBox);
            }
        }

        public interface ServiceClickListener {
                void onAdd(RequirementResponse itemsData);
                void onRemove(RequirementResponse itemsData);
        }

}
