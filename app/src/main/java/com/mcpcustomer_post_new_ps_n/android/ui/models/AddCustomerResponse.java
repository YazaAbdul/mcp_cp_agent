package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class AddCustomerResponse implements Serializable {

    private String status;
    private String msg;

    public AddCustomerResponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
