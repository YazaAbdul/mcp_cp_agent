package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class MelaPlansResponse implements Serializable {



    private String s_no;
    private String created_date;
    private String mela_id;
    private String mela_name;
    private String agent_id;
    private String agent_name;
    private String associate_count;
    private String customer_count;

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getMela_id() {
        return mela_id;
    }

    public void setMela_id(String mela_id) {
        this.mela_id = mela_id;
    }

    public String getMela_name() {
        return mela_name;
    }

    public void setMela_name(String mela_name) {
        this.mela_name = mela_name;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAssociate_count() {
        return associate_count;
    }

    public void setAssociate_count(String associate_count) {
        this.associate_count = associate_count;
    }

    public String getCustomer_count() {
        return customer_count;
    }

    public void setCustomer_count(String customer_count) {
        this.customer_count = customer_count;
    }
}
