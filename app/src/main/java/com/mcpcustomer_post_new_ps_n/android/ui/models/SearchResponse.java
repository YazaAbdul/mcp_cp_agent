package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class SearchResponse {

    /*customer_name: "twst",
actvty_dt: "27 - Dec",
customer_id: "1",
mobile: "9666669835",
email: "ug",
activity: "S.V.F",
activity_date: "27 - Dec",
remarks: "",
project_name: "1"*/

    private String customer_name;
    private String actvty_dt;
    private String customer_id;
    private String mobile;
    private String email;
    private String activity;
    private String activity_date;
    private String remarks;
    private String project_name;

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getActvty_dt() {
        return actvty_dt;
    }

    public void setActvty_dt(String actvty_dt) {
        this.actvty_dt = actvty_dt;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}
