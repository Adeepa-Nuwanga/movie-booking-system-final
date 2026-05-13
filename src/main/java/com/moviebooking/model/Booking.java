package com.moviebooking.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    private final String bookingId;
    private final String requestId;

    private final String userKey;

    private final String customerName;
    private final String customerEmail;

    private final String movieId;
    private final String movieTitle;

    private final String showtimeId;
    private final String showtimeDate;
    private final String showtimeTime;

    private final String cinemaHall;

    private final List<String> seats;

    private final double totalPrice;

    private final LocalDateTime confirmedAt;

    private final BookingStatus status;

    /**
     * Full constructor
     */
    public Booking(String bookingId,
                   String requestId,
                   String userKey,
                   String customerName,
                   String customerEmail,
                   String movieId,
                   String movieTitle,
                   String showtimeId,
                   String showtimeDate,
                   String showtimeTime,
                   String cinemaHall,
                   List<String> seats,
                   double totalPrice,
                   LocalDateTime confirmedAt,
                   BookingStatus status) {

        this.bookingId = bookingId;
        this.requestId = requestId;

        this.userKey = userKey;

        this.customerName = customerName;
        this.customerEmail = customerEmail;

        this.movieId = movieId;
        this.movieTitle = movieTitle;

        this.showtimeId = showtimeId;
        this.showtimeDate = showtimeDate;
        this.showtimeTime = showtimeTime;

        this.cinemaHall = cinemaHall;

        this.seats = new ArrayList<>(seats);

        this.totalPrice = totalPrice;

        this.confirmedAt = confirmedAt;

        this.status = status;
    }

    /**
     * Compatibility constructor for Component 04 integration
     */
    public Booking(String bookingId, BookingRequest request) {

        this.bookingId = bookingId;

        this.requestId = request.getRequestId();

        this.userKey = request.getCustomerEmail();

        this.customerName = request.getCustomerName();
        this.customerEmail = request.getCustomerEmail();

        this.movieId = request.getMovieId();
        this.movieTitle = request.getMovieId();

        this.showtimeId = request.getShowtimeId();

        this.showtimeDate = "TBD";
        this.showtimeTime = "TBD";

        this.cinemaHall = "Main Hall";

        this.seats = new ArrayList<>(request.getSelectedSeats());

        this.totalPrice = request.getTotalPrice();

        this.confirmedAt = LocalDateTime.now();

        this.status = BookingStatus.CONFIRMED;
    }

    /**
     * Returns cancelled immutable copy
     */
    public Booking cancelledCopy() {
        return new Booking(
                bookingId,
                requestId,
                userKey,
                customerName,
                customerEmail,
                movieId,
                movieTitle,
                showtimeId,
                showtimeDate,
                showtimeTime,
                cinemaHall,
                seats,
                totalPrice,
                confirmedAt,
                BookingStatus.CANCELLED
        );
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public String getShowtimeDate() {
        return showtimeDate;
    }

    public String getShowtimeTime() {
        return showtimeTime;
    }

    public String getCinemaHall() {
        return cinemaHall;
    }

    public List<String> getSeats() {
        return new ArrayList<>(seats);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public BookingStatus getStatus() {
        return status;
    }
}