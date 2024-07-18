package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class ProjectImagesResponce implements Serializable {

    private String image;
    private String created_date;
    private String created_by;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
