package com.moviebooking.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private String bookingId;
    private String customerName;
    private String customerEmail;
    private String movieId;
    private String showtimeId;
    private List<String> seats;
    private double totalPrice;
    private LocalDateTime confirmedAt;
    private BookingStatus status;

    public Booking(String bookingId, BookingRequest request) {
        this.bookingId = bookingId;
        this.customerName = request.getCustomerName();
        this.customerEmail = request.getCustomerEmail();
        this.movieId = request.getMovieId();
        this.showtimeId = request.getShowtimeId();
        this.seats = request.getSelectedSeats();
        this.totalPrice = request.getTotalPrice();
        this.confirmedAt = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public List<String> getSeats() {
        return new ArrayList<>(seats);
    }

    public void setSeats(List<String> seats) {
        this.seats = new ArrayList<>(seats);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
