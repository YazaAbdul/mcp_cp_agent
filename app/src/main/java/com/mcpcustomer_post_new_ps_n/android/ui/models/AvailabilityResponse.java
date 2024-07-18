package com.mcpcustomer_post_new_ps_n.android.ui.models;

/**
 * Created by venkei on 05-Apr-19.
 */
public class AvailabilityResponse {

//    project_id: "1",
//project_name: "Saphire"
//},

    private String project_id;
    private String project_name;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public AvailabilityResponse(String project_id, String project_name) {
        this.project_id = project_id;
        this.project_name = project_name;
    }
}
