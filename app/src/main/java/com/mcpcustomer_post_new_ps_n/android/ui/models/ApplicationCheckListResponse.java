package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class ApplicationCheckListResponse {

    /*status: 1,
msg: "Updated Successfully"*/

    private int status;
    private String msg;

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
