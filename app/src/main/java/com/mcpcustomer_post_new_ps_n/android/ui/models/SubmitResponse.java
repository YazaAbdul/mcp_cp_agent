package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class SubmitResponse {

    /*application_status: 0,
status: 1,
msg: "Updated Successfully"*/

    private String application_status;
    private int status;
    private String msg;

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
