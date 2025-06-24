package com.example.finalproject_ucas.model;

public class Booking {
    private int bookingId;
    private String roomType;
    private String checkIn;
    private String checkOut;
    private int guests;
    private String status;

    public Booking(int bookingId, String roomType, String checkIn, String checkOut, int guests, String status) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.status = status;
    }

    // Getters
    public int getBookingId() { return bookingId; }
    public String getRoomType() { return roomType; }
    public String getCheckIn() { return checkIn; }
    public String getCheckOut() { return checkOut; }
    public int getGuests() { return guests; }
    public String getStatus() { return status; }
}
