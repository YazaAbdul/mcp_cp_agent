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
import com.mcpcustomer_post_new_ps_n.android.ui.models.DesignationsResponse;

import java.util.List;

/**
 * Created by venkei on 19-Mar-19.
 */
public class DesignationsAdapter extends ArrayAdapter<DesignationsResponse> {

    List<DesignationsResponse> sourceResponseList;

    public DesignationsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DesignationsResponse> sourceResponseList) {
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
        DesignationsResponse category = getItem(position);
        DesignationsAdapter.ParentViewHolder parentViewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_view, null);
            parentViewHolder = new DesignationsAdapter.ParentViewHolder(view);
            view.setTag(parentViewHolder);
        } else {
            parentViewHolder = (DesignationsAdapter.ParentViewHolder) view.getTag();
        }
        parentViewHolder.spinnerText.setText(TextUtils.isEmpty(category.getDesignation_title()) ? "" : category.getDesignation_title());
        return view;
    }


    @Override
    public View getDropDownView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        DesignationsResponse category = getItem(position);
        DesignationsAdapter.ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_list_items, null);
            holder = new DesignationsAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (DesignationsAdapter.ViewHolder) view.getTag();
        }
        holder.spinnerText.setText(TextUtils.isEmpty(category.getDesignation_title()) ? "" : category.getDesignation_title());
        return view;
    }
}

