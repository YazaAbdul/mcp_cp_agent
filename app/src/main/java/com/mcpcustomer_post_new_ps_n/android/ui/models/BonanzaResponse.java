package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class BonanzaResponse implements Serializable {

    private String bonanza_start;
    private String bonanza_end;
    private String notes;
    private String pic;
    private String no_of_sales;

    public BonanzaResponse(String bonanza_start, String bonanza_end, String notes, String pic, String no_of_sales) {
        this.bonanza_start = bonanza_start;
        this.bonanza_end = bonanza_end;
        this.notes = notes;
        this.pic = pic;
        this.no_of_sales = no_of_sales;
    }

    public String getNo_of_sales() {
        return no_of_sales;
    }

    public void setNo_of_sales(String no_of_sales) {
        this.no_of_sales = no_of_sales;
    }

    public String getBonanza_start() {
        return bonanza_start;
    }

    public void setBonanza_start(String bonanza_start) {
        this.bonanza_start = bonanza_start;
    }

    public String getBonanza_end() {
        return bonanza_end;
    }

    public void setBonanza_end(String bonanza_end) {
        this.bonanza_end = bonanza_end;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
