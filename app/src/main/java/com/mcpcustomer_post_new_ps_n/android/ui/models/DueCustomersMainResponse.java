package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;
import java.util.ArrayList;

public class DueCustomersMainResponse implements Serializable {

    private String project_id;
    private String project_name;
    private String count;
    ArrayList<DueCustomersResponse> cust = new ArrayList<>();

    public DueCustomersMainResponse(String project_id, String project_name, String count, ArrayList<DueCustomersResponse> cust) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.count = count;
        this.cust = cust;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<DueCustomersResponse> getCust() {
        return cust;
    }

    public void setCust(ArrayList<DueCustomersResponse> cust) {
        this.cust = cust;
    }
}
