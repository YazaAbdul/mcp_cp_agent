package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class ProjectMenuResponce implements Serializable {

    private String menu_id;
    private String bhk;
    private String price;
    private String address;
    private String menu_name;
    private String location;
    private String menu_icon;
    private String brochure_dtl;
    private String layout_dtl;
    private String application_dtl;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getBhk() {
        return bhk;
    }

    public void setBhk(String bhk) {
        this.bhk = bhk;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMenu_icon() {
        return menu_icon;
    }

    public void setMenu_icon(String menu_icon) {
        this.menu_icon = menu_icon;
    }

    public String getBrochure_dtl() {
        return brochure_dtl;
    }

    public void setBrochure_dtl(String brochure_dtl) {
        this.brochure_dtl = brochure_dtl;
    }

    public String getLayout_dtl() {
        return layout_dtl;
    }

    public void setLayout_dtl(String layout_dtl) {
        this.layout_dtl = layout_dtl;
    }

    public String getApplication_dtl() {
        return application_dtl;
    }

    public void setApplication_dtl(String application_dtl) {
        this.application_dtl = application_dtl;
    }
}
