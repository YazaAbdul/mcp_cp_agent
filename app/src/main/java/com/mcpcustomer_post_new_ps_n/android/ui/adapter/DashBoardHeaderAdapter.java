package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.ui.models.DashboardHeaderResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by venkei on 19-Mar-19.
 */
public class DashBoardHeaderAdapter implements ExpandableListAdapter {

    private Activity context;
    private List<DashboardHeaderResponse> brands = new ArrayList<DashboardHeaderResponse>();
    private LinearLayoutManager layoutManager;

    public DashBoardHeaderAdapter(Activity context, List<DashboardHeaderResponse> brands) {
        this.context = context;
        this.brands = brands;
    }

    public DashBoardHeaderAdapter(Context applicationContext, List<DashboardHeaderResponse> headerResponses) {
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return brands.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return brands.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return brands.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ParentHolder parentHolder = null;

        DashboardHeaderResponse brand = (DashboardHeaderResponse) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater userInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = userInflater.inflate(R.layout.cp_dashboard_header_list_item, null);
            convertView.setHorizontalScrollBarEnabled(true);

            parentHolder = new ParentHolder();
            convertView.setTag(parentHolder);

        } else {
            parentHolder = (ParentHolder) convertView.getTag();
        }

        parentHolder.activityNameTVID = convertView.findViewById(R.id.activityNameTVID);
        parentHolder.activityNameTVID.setText(brand.getPlan_name());
        parentHolder.activityPic = convertView.findViewById(R.id.activityPic);
        //Picasso.with(context).load(brand.getGallery()).into(parentHolder.activityPic);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder childHolder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cp_dashbaord_child_view, parent, false);
            childHolder = new ChildHolder();
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.horizontalListView = convertView.findViewById(R.id.dashboardRVID);

        layoutManager = new GridLayoutManager(context, 2);
        //layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        childHolder.horizontalListView.setLayoutManager(layoutManager);
        DashBoardChildAdapter horizontalListAdapter = new DashBoardChildAdapter(context, brands.get(groupPosition).getMain());
        childHolder.horizontalListView.setAdapter(horizontalListAdapter);

        horizontalListAdapter.setChildClickListener(new DashBoardChildAdapter.ChildItemCLicListener() {
            @Override
            public void childItemClickedPosition(String name, String id, String count, int position) {

                String planID = brands.get(groupPosition).getPlan_id();

            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    private static class ChildHolder {
        static RecyclerView horizontalListView;
    }

    private static class ParentHolder {

        ImageView activityPic;
        TextView activityNameTVID;
    }
}

