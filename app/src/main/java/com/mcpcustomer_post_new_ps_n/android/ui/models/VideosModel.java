package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class VideosModel {

    private int videoImage;
    private String title;
    private String link;

    public VideosModel() {
    }

    public VideosModel(int videoImage, String title, String link) {
        this.videoImage = videoImage;
        this.title = title;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(int videoImage) {
        this.videoImage = videoImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
