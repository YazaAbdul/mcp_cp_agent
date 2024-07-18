package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class VeichleResponse {
    public String id;
    public String vehicle_name;
    public String seating_capacity;
    public String vehicle_pic;

    public VeichleResponse() {

    }


    public VeichleResponse(String id, String vehicle_name, String seating_capacity, String vehicle_pic) {
        this.id = id;
        this.vehicle_name = vehicle_name;
        this.seating_capacity = seating_capacity;
        this.vehicle_pic = vehicle_pic;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }
    public String getSeating_capacity() {
        return seating_capacity;
    }

    public void setSeating_capacity(String seating_capacity) {
        this.seating_capacity = seating_capacity;
    }
    public String getVehicle_pic() {
        return vehicle_pic;
    }

    public void setVehicle_pic(String vehicle_pic) {
        this.vehicle_pic = vehicle_pic;
    }

}
