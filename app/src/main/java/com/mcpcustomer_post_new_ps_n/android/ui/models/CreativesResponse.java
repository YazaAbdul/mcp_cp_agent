package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class CreativesResponse implements Serializable {

    /*s_no": "1",
    "image":*/

    private String s_no;
    private String image;
    private String video_url;


    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public CreativesResponse(String s_no, String image) {
        this.s_no = s_no;
        this.image = image;
    }

    public CreativesResponse() {

    }

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
