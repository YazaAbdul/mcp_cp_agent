package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.ProjectsResponse;

import java.util.List;

/**
 * Created by venkei on 19-Mar-19.
 */
public class ProjectsPurchaseAdapter extends ArrayAdapter<ProjectsResponse> {

    List<ProjectsResponse> sourceResponseList;

    public ProjectsPurchaseAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ProjectsResponse> sourceResponseList) {
        super(context, resource, sourceResponseList);
        this.sourceResponseList = sourceResponseList;
    }

    @Override
    public int getCount() {

        try {
            if (sourceResponseList!=null){
                return sourceResponseList.size();
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
        private ImageView dropdown;

        public ParentViewHolder(View view) {
            spinnerText = view.findViewById(R.id.spinnerItems);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ProjectsResponse category = getItem(position);
        ProjectsPurchaseAdapter.ParentViewHolder parentViewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_view, null);
            parentViewHolder = new ProjectsPurchaseAdapter.ParentViewHolder(view);
            view.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ProjectsPurchaseAdapter.ParentViewHolder) view.getTag();
        }
        parentViewHolder.spinnerText.setText(TextUtils.isEmpty(category.getProject_name()) ? "" : category.getProject_name());
        return view;
    }


    @Override
    public View getDropDownView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ProjectsResponse category = getItem(position);
        ProjectsPurchaseAdapter.ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_list_items, null);
            holder = new ProjectsPurchaseAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ProjectsPurchaseAdapter.ViewHolder) view.getTag();
        }
        holder.spinnerText.setText(TextUtils.isEmpty(category.getProject_name()) ? "" : category.getProject_name());
        return view;
    }
}

