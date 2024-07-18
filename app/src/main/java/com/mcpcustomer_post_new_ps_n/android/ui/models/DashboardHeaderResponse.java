package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.util.ArrayList;

/**
 * Created by venkei on 19-Mar-19.
 */
public class DashboardHeaderResponse {

    private String plan_id;
    private String plan_name;
    private String Gallery;
    private ArrayList<DashboardChildResponse> Main=new ArrayList<>();

    public DashboardHeaderResponse(String plan_id, String plan_name, String gallery, ArrayList<DashboardChildResponse> main) {
        this.plan_id = plan_id;
        this.plan_name = plan_name;
        Gallery = gallery;
        Main = main;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getGallery() {
        return Gallery;
    }

    public void setGallery(String gallery) {
        Gallery = gallery;
    }

    public ArrayList<DashboardChildResponse> getMain() {
        return Main;
    }

    public void setMain(ArrayList<DashboardChildResponse> main) {
        Main = main;
    }
}
