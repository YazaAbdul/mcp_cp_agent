package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class EligibleCommissionResponse implements Serializable {

    private String defalt_eligiblecomm;
    private String total_eligiblecomm;
    private String already_taken;
    private String total_eligiblecomm__already_taken;
    private String half_eligiblecomm;
    private String eligiblecommission;

    public EligibleCommissionResponse(String defalt_eligiblecomm, String total_eligiblecomm, String already_taken, String total_eligiblecomm__already_taken, String half_eligiblecomm, String eligiblecommission) {
        this.defalt_eligiblecomm = defalt_eligiblecomm;
        this.total_eligiblecomm = total_eligiblecomm;
        this.already_taken = already_taken;
        this.total_eligiblecomm__already_taken = total_eligiblecomm__already_taken;
        this.half_eligiblecomm = half_eligiblecomm;
        this.eligiblecommission = eligiblecommission;
    }

    public String getDefalt_eligiblecomm() {
        return defalt_eligiblecomm;
    }

    public void setDefalt_eligiblecomm(String defalt_eligiblecomm) {
        this.defalt_eligiblecomm = defalt_eligiblecomm;
    }

    public String getTotal_eligiblecomm() {
        return total_eligiblecomm;
    }

    public void setTotal_eligiblecomm(String total_eligiblecomm) {
        this.total_eligiblecomm = total_eligiblecomm;
    }

    public String getAlready_taken() {
        return already_taken;
    }

    public void setAlready_taken(String already_taken) {
        this.already_taken = already_taken;
    }

    public String getTotal_eligiblecomm__already_taken() {
        return total_eligiblecomm__already_taken;
    }

    public void setTotal_eligiblecomm__already_taken(String total_eligiblecomm__already_taken) {
        this.total_eligiblecomm__already_taken = total_eligiblecomm__already_taken;
    }

    public String getHalf_eligiblecomm() {
        return half_eligiblecomm;
    }

    public void setHalf_eligiblecomm(String half_eligiblecomm) {
        this.half_eligiblecomm = half_eligiblecomm;
    }

    public String getEligiblecommission() {
        return eligiblecommission;
    }

    public void setEligiblecommission(String eligiblecommission) {
        this.eligiblecommission = eligiblecommission;
    }
}
