package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class ScrollerResponse implements Serializable {

    private String id;
    private String text;

    public ScrollerResponse(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
