package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class AllStatusResponse {

    /*
msg: 1,
error_text: "Success"*/
    private int msg;
    private String error_text;

    public int getMsg() {
        return msg;
    }

    public void getMsg(int mgs) {
        this.msg = mgs;
    }

    public String getError_text() {
        return error_text;
    }

    public void setError_text(String error_text) {
        this.error_text = error_text;
    }
}
