package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class ClubDetailsResponse implements Serializable {


    /*"s_no": "1",
    "achievement_target": "One Direct Unit Sale",
    "achievement_rank": "Imark level - I",
    "club_benefits": "Executive Kit",
    "ach": "13"*/

    private String s_no;
    private String achievement_target;
    private String achievement_rank;
    private String club_benefits;
    private String ach;





    public ClubDetailsResponse(String s_no, String achievement_target, String achievement_rank, String club_benefits, String ach) {
        this.s_no = s_no;
        this.achievement_target = achievement_target;
        this.achievement_rank = achievement_rank;
        this.club_benefits = club_benefits;
        this.ach = ach;
    }

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public String getAchievement_target() {
        return achievement_target;
    }

    public void setAchievement_target(String achievement_target) {
        this.achievement_target = achievement_target;
    }

    public String getAchievement_rank() {
        return achievement_rank;
    }

    public void setAchievement_rank(String achievement_rank) {
        this.achievement_rank = achievement_rank;
    }

    public String getClub_benefits() {
        return club_benefits;
    }

    public void setClub_benefits(String club_benefits) {
        this.club_benefits = club_benefits;
    }

    public String getAch() {
        return ach;
    }

    public void setAch(String ach) {
        this.ach = ach;
    }
}
