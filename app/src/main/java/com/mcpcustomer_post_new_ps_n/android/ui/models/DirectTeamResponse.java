package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class DirectTeamResponse implements Serializable {

    private int s_no;
    private String agent_id;
    private String associate;
    private String Designation;
    private String id;
    private String mobile_no;
    private String sales;
    private int sale_amount;
    private String upline_code;
    private String upline_name;
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

  /*  public DirectTeamResponse(String associate, String designation, String id, String mobile_no, String sales, String sale_amount, String upline_code, String upline_name) {
        this.associate = associate;
        Designation = designation;
        this.id = id;
        this.mobile_no = mobile_no;
        this.sales = sales;
        this.sale_amount = sale_amount;
        this.upline_code = upline_code;
        this.upline_name = upline_name;
    }
*/
    public int getS_no() {
        return s_no;
    }

    public void setS_no(int s_no) {
        this.s_no = s_no;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getAssociate() {
        return associate;
    }

    public void setAssociate(String associate) {
        this.associate = associate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public int getSale_amount() {
        return sale_amount;
    }

    public void setSale_amount(int sale_amount) {
        this.sale_amount = sale_amount;
    }

    public String getUpline_code() {
        return upline_code;
    }

    public void setUpline_code(String upline_code) {
        this.upline_code = upline_code;
    }

    public String getUpline_name() {
        return upline_name;
    }

    public void setUpline_name(String upline_name) {
        this.upline_name = upline_name;
    }
}
