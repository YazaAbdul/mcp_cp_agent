package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class DesignationsResponse implements Serializable {


    private String designation_id;
    private String designation_title;

    public DesignationsResponse(String designation_id, String designation_title) {
        this.designation_id = designation_id;
        this.designation_title = designation_title;
    }

    public String getDesignation_id() {
        return designation_id;
    }

    public void setDesignation_id(String designation_id) {
        this.designation_id = designation_id;
    }

    public String getDesignation_title() {
        return designation_title;
    }

    public void setDesignation_title(String designation_title) {
        this.designation_title = designation_title;
    }
}
