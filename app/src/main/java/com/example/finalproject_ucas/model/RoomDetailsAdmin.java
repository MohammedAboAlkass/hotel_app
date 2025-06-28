package com.example.finalproject_ucas.model;

import android.net.Uri;
public class RoomDetailsAdmin {

    private int roomId;
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private int availability;
    private String imageUri;
    private String desc;


    // Constructor
    public RoomDetailsAdmin(int roomId, String roomNumber, String roomType, double pricePerNight, int availability, String imageUri, String desc) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.availability = availability;
        this.imageUri = imageUri;
        this.desc = desc;
    }

    // Constructor بدون ID (في حالة الإدخال الجديد)
    public RoomDetailsAdmin(String roomNumber, String roomType, double pricePerNight, int availability, String imageUri, String desc) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.availability = availability;
        this.imageUri = imageUri;
        this.desc = desc;
    }

    // Getters
    public int getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getAvailability() {
        return availability;
    }

    public String getImageUri() {
        return imageUri;
    }

    // Setters
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
