package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectDashBoardResponce implements Serializable {

    private ArrayList<MenuResponce> menu = new ArrayList<>();
    private ArrayList<ProjectImagesResponce> project_images = new ArrayList<>();
    private ArrayList<FloorPlansResponce> floor_plans = new ArrayList<>();
    private ArrayList<VideosResponce> project_vedios = new ArrayList<>();
    private ArrayList<AmenitiesResponce> ammenities = new ArrayList<>();

    public ArrayList<MenuResponce> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuResponce> menu) {
        this.menu = menu;
    }

    public ArrayList<ProjectImagesResponce> getProject_images() {
        return project_images;
    }

    public void setProject_images(ArrayList<ProjectImagesResponce> project_images) {
        this.project_images = project_images;
    }

    public ArrayList<FloorPlansResponce> getFloor_plans() {
        return floor_plans;
    }

    public void setFloor_plans(ArrayList<FloorPlansResponce> floor_plans) {
        this.floor_plans = floor_plans;
    }

    public ArrayList<VideosResponce> getProject_vedios() {
        return project_vedios;
    }

    public void setProject_vedios(ArrayList<VideosResponce> project_vedios) {
        this.project_vedios = project_vedios;
    }

    public ArrayList<AmenitiesResponce> getAmmenities() {
        return ammenities;
    }

    public void setAmmenities(ArrayList<AmenitiesResponce> ammenities) {
        this.ammenities = ammenities;
    }
}
