package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class SiteVisitResponse implements Serializable {


    private String agent_id;
    private String agent_name;
    private String agent_number;
    private String customer_name;
    private String customer_number;
    private String project_name;
    private String upload_pic;
    private String date_time;

    public SiteVisitResponse(String agent_id, String agent_name, String agent_number, String customer_name, String customer_number, String project_name, String upload_pic, String date_time) {
        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.agent_number = agent_number;
        this.customer_name = customer_name;
        this.customer_number = customer_number;
        this.project_name = project_name;
        this.upload_pic = upload_pic;
        this.date_time = date_time;
    }

    public SiteVisitResponse() {
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

    public String getAgent_number() {
        return agent_number;
    }

    public void setAgent_number(String agent_number) {
        this.agent_number = agent_number;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getUpload_pic() {
        return upload_pic;
    }

    public void setUpload_pic(String upload_pic) {
        this.upload_pic = upload_pic;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
