package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectsResponse;

import java.util.List;


/**
 * Created by venkei on 04-Mar-19.
 */
public class LeadActivitiesAdapter extends ArrayAdapter<com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectsResponse> {
    List<com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectsResponse> ProjectsResponse;

    public LeadActivitiesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ProjectsResponse> ProjectsResponse) {
        super(context, resource, ProjectsResponse);
        this.ProjectsResponse = ProjectsResponse;
    }

    @Override
    public int getCount() {
        try {
            if (ProjectsResponse !=null){
                return ProjectsResponse.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public class ViewHolder {
        public TextView spinnerText;


        public ViewHolder(View view) {
            spinnerText =  view.findViewById(R.id.spinnerText);

        }
    }

    public class ParentViewHolder {
        public TextView spinnerText;

        public ParentViewHolder(View view) {
            spinnerText = view.findViewById(R.id.spinnerItems);
        }
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ProjectsResponse category = getItem(position);
        ParentViewHolder parentViewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cp_custom_spinner_view, null);
            parentViewHolder = new ParentViewHolder(view);
            view.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) view.getTag();
        }
        parentViewHolder.spinnerText.setText(TextUtils.isEmpty(category.getProject_name()) ? "" : category.getProject_name());
        return view;
    }


    @SuppressLint("InflateParams")
    @Override
    public View getDropDownView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ProjectsResponse category = getItem(position);
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cp_custom_spinner_list_items, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.spinnerText.setText(TextUtils.isEmpty(category.getProject_name()) ? "" : category.getProject_name());
        return view;
    }
}


