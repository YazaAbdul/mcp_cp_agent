package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class AmenitiesResponce implements Serializable {

    private String ammenities_img;
    private String ammenities_nm;

    public String getAmmenities_nm() {
        return ammenities_nm;
    }

    public void setAmmenities_nm(String ammenities_nm) {
        this.ammenities_nm = ammenities_nm;
    }

    public String getAmmenities_img() {
        return ammenities_img;
    }

    public void setAmmenities_img(String ammenities_img) {
        this.ammenities_img = ammenities_img;
    }
}
