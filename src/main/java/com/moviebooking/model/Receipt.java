package com.moviebooking.model;

public class Receipt {
    private final Booking booking;
    private final String seatText;
    private final String totalText;
    private final String confirmedAtText;

    public Receipt(Booking booking, String seatText, String totalText, String confirmedAtText) {
        this.booking = booking;
        this.seatText = seatText;
        this.totalText = totalText;
        this.confirmedAtText = confirmedAtText;
    }

    public Booking getBooking() {
        return booking;
    }

    public String getSeatText() {
        return seatText;
    }

    public String getTotalText() {
        return totalText;
    }

    public String getConfirmedAtText() {
        return confirmedAtText;
    }
}
