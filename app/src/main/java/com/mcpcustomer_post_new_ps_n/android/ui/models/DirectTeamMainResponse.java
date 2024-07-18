package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;
import java.util.ArrayList;

public class DirectTeamMainResponse implements Serializable {

    private DirectTeamCountResponse count;
    private ArrayList<DirectTeamResponse> associates = new ArrayList<>();

    public DirectTeamCountResponse getCount() {
        return count;
    }

    public void setCount(DirectTeamCountResponse count) {
        this.count = count;
    }

    public ArrayList<DirectTeamResponse> getAssociates() {
        return associates;
    }

    public void setAssociates(ArrayList<DirectTeamResponse> associates) {
        this.associates = associates;
    }
}
