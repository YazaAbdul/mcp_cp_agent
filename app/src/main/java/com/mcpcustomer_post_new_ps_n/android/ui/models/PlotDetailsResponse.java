package com.mcpcustomer_post_new_ps_n.android.ui.models;

/**
 * Created by venkei on 08-Apr-19.
 */
public class PlotDetailsResponse {

    /*plot_id: "1",
plot_number: "7",
sqyd: "600",
unit_price: "6500",
facing: "West-60ft Rd",
premium: "500",
total_price: "7000",
total_amount: "4200000"*/

    private String plot_id;
    private String unit;
    private String facing;
    private String bhk;
    private String floor;
    private String builtup;
    private String unit_price;
    private String unit_cost;
    private String premium;
    private String ammenities;
    private String infra;
    private String total;
    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public PlotDetailsResponse(String plot_id, String unit, String facing, String bhk, String floor, String builtup, String unit_price, String unit_cost, String premium, String ammenities, String infra, String total ) {
        this.plot_id = plot_id;
        this.unit = unit;
        this.facing = facing;
        this.bhk = bhk;
        this.floor = floor;
        this.builtup = builtup;
        this.unit_price = unit_price;
        this.unit_cost = unit_cost;
        this.premium = premium;
        this.ammenities = ammenities;
        this.infra = infra;
        this.total = total;

    }


    public String getPlot_id() {
        return plot_id;
    }

    public void setPlot_id(String plot_id) {
        this.plot_id = plot_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public String getBhk() {
        return bhk;
    }

    public void setBhk(String bhk) {
        this.bhk = bhk;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuiltup() {
        return builtup;
    }

    public void setBuiltup(String builtup) {
        this.builtup = builtup;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(String unit_cost) {
        this.unit_cost = unit_cost;
    }
    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }
    public String getAmmenities() {
        return ammenities;
    }

    public void setAmmenities(String ammenities) {
        this.ammenities = ammenities;
    }
    public String getInfra() {
        return infra;
    }

    public void setInfra(String infra) {
        this.infra = infra;
    }
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
