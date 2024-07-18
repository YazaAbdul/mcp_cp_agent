package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class SiteInchargeResponse implements Serializable {

    private String site_id;
    private String site_incharge;
    private String mobile;
    private String email;
    private String project_id;

    public SiteInchargeResponse(String site_id, String site_incharge, String mobile, String email, String project_id) {
        this.site_id = site_id;
        this.site_incharge = site_incharge;
        this.mobile = mobile;
        this.email = email;
        this.project_id = project_id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getSite_incharge() {
        return site_incharge;
    }

    public void setSite_incharge(String site_incharge) {
        this.site_incharge = site_incharge;
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

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
}
