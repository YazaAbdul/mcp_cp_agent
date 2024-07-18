package com.mcpcustomer_post_new_ps_n.android.ui.models;


public class AddSaleHistoryResponse {

    public String agent_count;
    public String team_sale;
    public String total_sale;
    public String received_amt;
    public String due_amt;
    public String comm;
    public String eligible;
    public String comm_paid;
    public String p_due;
    public String comm_due;


    public AddSaleHistoryResponse(String agent_count, String team_sale, String total_sale, String received_amt, String due_amt, String comm, String eligible, String comm_paid, String p_due, String comm_due) {
        this.agent_count = agent_count;
        this.team_sale = team_sale;
        this.total_sale = total_sale;
        this.received_amt = received_amt;
        this.due_amt = due_amt;
        this.comm = comm;
        this.eligible = eligible;
        this.comm_paid = comm_paid;
        this.p_due = p_due;
        this.comm_due = comm_due;
    }

    public String getTeam_sale() {
        return team_sale;
    }

    public void setTeam_sale(String team_sale) {
        this.team_sale = team_sale;
    }

    public String getAgent_count() {
        return agent_count;
    }

    public void setAgent_count(String agent_count) {
        this.agent_count = agent_count;
    }
    public String getTotal_sale() {
        return total_sale;
    }

    public void setTotal_sale(String total_sale) {
        this.total_sale = total_sale;
    }
    public String getReceived_amt() {
        return received_amt;
    }

    public void setReceived_amt(String received_amt) {
        this.received_amt = received_amt;
    }
    public String getDue_amt() {
        return due_amt;
    }

    public void setDue_amt(String due_amt) {
        this.due_amt = due_amt;
    }
    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }
    public String getEligible() {
        return eligible;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
    }
    public String getComm_paid() {
        return comm_paid;
    }

    public void setComm_paid(String comm_paid) {
        this.comm_paid = comm_paid;
    }
    public String getP_due() {
        return p_due;
    }

    public void setP_due(String p_due) {
        this.p_due = p_due;
    }
    public String getComm_due() {
        return comm_due;
    }

    public void setComm_due(String comm_due) {
        this.comm_due = comm_due;
    }


}
