package com.mcpcustomer_post_new_ps_n.android.ui.models;

/**
 * Created by venkei on 05-Apr-19.
 */
public class AvailableDetailsResponse {

//    plot_id: "1",
//plot_number: "1",
//color: ""

    private String plot_id;
    private String plot_number;
    private String color;

    public AvailableDetailsResponse(String plot_id, String plot_number, String color) {
        this.plot_id = plot_id;
        this.plot_number = plot_number;
        this.color = color;
    }

    public String getPlot_id() {
        return plot_id;
    }

    public void setPlot_id(String plot_id) {
        this.plot_id = plot_id;
    }

    public String getPlot_number() {
        return plot_number;
    }

    public void setPlot_number(String plot_number) {
        this.plot_number = plot_number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
