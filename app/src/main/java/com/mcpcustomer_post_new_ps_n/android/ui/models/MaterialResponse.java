package com.mcpcustomer_post_new_ps_n.android.ui.models;

import java.io.Serializable;

public class MaterialResponse implements Serializable {


    private String material_id;
    private String category_name;
    private String unit_price;

    public MaterialResponse(String material_id, String category_name, String unit_price) {
        this.material_id = material_id;
        this.category_name = category_name;
        this.unit_price = unit_price;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
