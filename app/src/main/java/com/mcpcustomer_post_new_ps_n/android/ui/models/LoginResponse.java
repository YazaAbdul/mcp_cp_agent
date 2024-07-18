package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class LoginResponse {

    private int status;
    private String cp_id;
    private String user_email;
    private String mobile;
    private String designation;
    private String designation_name;
    private int travel_eligible;
    private String user_name;
    private String upload_pic;
    private String care_taker;
    private String agent_unique_id;

    public String getAgent_unique_id() {
        return agent_unique_id;
    }

    public void setAgent_unique_id(String agent_unique_id) {
        this.agent_unique_id = agent_unique_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation_name() {
        return designation_name;
    }

    public void setDesignation_name(String designation_name) {
        this.designation_name = designation_name;
    }

    public int getTravel_eligible() {
        return travel_eligible;
    }

    public void setTravel_eligible(int travel_eligible) {
        this.travel_eligible = travel_eligible;
    }

    public String getUpload_pic() {
        return upload_pic;
    }

    public void setUpload_pic(String upload_pic) {
        this.upload_pic = upload_pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCp_id() {
        return cp_id;
    }

    public void setCp_id(String cp_id) {
        this.cp_id = cp_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCare_taker() {
        return care_taker;
    }

    public void setCare_taker(String care_taker) {
        this.care_taker = care_taker;
    }
}
