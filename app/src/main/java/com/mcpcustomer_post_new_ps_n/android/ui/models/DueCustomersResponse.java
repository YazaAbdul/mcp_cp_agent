package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class DueCustomersResponse implements Serializable {

    private String customer_id;
    private String customer_name;
    private String mobile_no;


    public DueCustomersResponse(String customer_id, String customer_name, String mobile_no) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.mobile_no = mobile_no;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
