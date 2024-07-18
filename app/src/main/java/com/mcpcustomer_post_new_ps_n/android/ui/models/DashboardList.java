package com.mcpcustomer_post_new_ps_n.android.ui.models;

/**
 * Created by venkei on 20-Jan-18.
 */

public class DashboardList {

    private String dasTittle;
    private String dasDesc;
    private String dasImage;
    private String content_type;
    private String video;
    private String thumbnail;
    private String lat;
    private String lan;
    private String strtext;

    public DashboardList(String dasTittle, String dasDesc, String dasImage, String content_type, String video, String thumbnail, String lat, String lan, String strtext) {
        this.dasTittle = dasTittle;
        this.dasDesc = dasDesc;
        this.dasImage = dasImage;
        this.content_type = content_type;
        this.video = video;
        this.thumbnail = thumbnail;
        this.lat = lat;
        this.lan = lan;
        this.strtext = strtext;
    }

    public DashboardList(String strtext, String tittle, String descrption, String imagelink, String video, String thumbnail, String lat, String lan) {
    }

    public String getStrtext() {
        return strtext;
    }

    public void setStrtext(String strtext) {
        this.strtext = strtext;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }


    public String getDasTittle() {
        return dasTittle;
    }

    public void setDasTittle(String dasTittle) {
        this.dasTittle = dasTittle;
    }

    public String getDasDesc() {
        return dasDesc;
    }

    public void setDasDesc(String dasDesc) {
        this.dasDesc = dasDesc;
    }

    public String getDasImage() {
        return dasImage;
    }

    public void setDasImage(String dasImage) {
        this.dasImage = dasImage;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}
