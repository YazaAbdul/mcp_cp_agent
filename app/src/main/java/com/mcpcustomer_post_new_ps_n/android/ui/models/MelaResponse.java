package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class MelaResponse implements Serializable {

    private String s_no;
    private String mela_title;
    private String mela_description;
    private String mela_date;
    private String mobile_logic;
    private String mela_id;
    private String agent_id;
    private String customer_count;
    private String associate_count;
    private String created_date;



    public String getMela_id() {
        return mela_id;
    }

    public void setMela_id(String mela_id) {
        this.mela_id = mela_id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getCustomer_count() {
        return customer_count;
    }

    public void setCustomer_count(String customer_count) {
        this.customer_count = customer_count;
    }

    public String getAssociate_count() {
        return associate_count;
    }

    public void setAssociate_count(String associate_count) {
        this.associate_count = associate_count;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getMobile_logic() {
        return mobile_logic;
    }

    public void setMobile_logic(String mobile_logic) {
        this.mobile_logic = mobile_logic;
    }

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public String getMela_title() {
        return mela_title;
    }

    public void setMela_title(String mela_title) {
        this.mela_title = mela_title;
    }

    public String getMela_description() {
        return mela_description;
    }

    public void setMela_description(String mela_description) {
        this.mela_description = mela_description;
    }

    public String getMela_date() {
        return mela_date;
    }

    public void setMela_date(String mela_date) {
        this.mela_date = mela_date;
    }
}
