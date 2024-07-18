package com.mcpcustomer_post_new_ps_n.android.ui.models;

/**
 * Created by venkei on 21-May-19.
 */
public class BhkResponse {

    private String bhk_type;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BhkResponse(String bhk_type, String color) {
        this.bhk_type = bhk_type;
        this.color=color;
    }

    public String getBhk_type() {
        return bhk_type;
    }

    public void setBhk_type(String bhk_type) {
        this.bhk_type = bhk_type;
    }
}
