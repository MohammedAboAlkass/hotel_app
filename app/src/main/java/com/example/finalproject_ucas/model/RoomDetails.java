package com.example.finalproject_ucas.model;

import android.net.Uri;

public class RoomDetails {

    Uri img;
    double price;
    String type;
    String desc;

    public RoomDetails(Uri img, double price, String type, String desc) {
        this.img = img;
        this.price = price;
        this.type = type;
        this.desc = desc;
    }

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
