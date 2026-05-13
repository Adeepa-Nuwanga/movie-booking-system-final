package com.moviebooking.service;

import com.moviebooking.model.Booking;
import com.moviebooking.model.Receipt;

import java.time.format.DateTimeFormatter;

public class ReceiptBuilder {
    private static final DateTimeFormatter CONFIRMED_AT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private ReceiptBuilder() {
    }

    public static Receipt build(Booking booking) {
        String seatText = String.join(", ", booking.getSeats());
        String totalText = String.format("LKR %.2f", booking.getTotalPrice());
        String confirmedAtText = booking.getConfirmedAt().format(CONFIRMED_AT_FORMAT);
        return new Receipt(booking, seatText, totalText, confirmedAtText);
    }
}
