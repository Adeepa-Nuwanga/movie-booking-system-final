package com.moviebooking.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingRequest {
    private String requestId;
    private String customerName;
    private String customerEmail;
    private String movieId;
    private String showtimeId;
    private List<String> selectedSeats;
    private double totalPrice;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private String rejectionReason;

    public BookingRequest(String requestId, String customerName, String customerEmail, String movieId,
                          String showtimeId, List<String> selectedSeats, double totalPrice) {
        this.requestId = requestId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.movieId = movieId;
        this.showtimeId = showtimeId;
        this.selectedSeats = new ArrayList<>(selectedSeats);
        this.totalPrice = totalPrice;
        this.status = BookingStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void confirm() {
        this.status = BookingStatus.CONFIRMED;
        this.processedAt = LocalDateTime.now();
        this.rejectionReason = null;
    }

    public void reject(String reason) {
        this.status = BookingStatus.REJECTED;
        this.processedAt = LocalDateTime.now();
        this.rejectionReason = reason;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public List<String> getSelectedSeats() {
        return new ArrayList<>(selectedSeats);
    }

    public void setSelectedSeats(List<String> selectedSeats) {
        this.selectedSeats = new ArrayList<>(selectedSeats);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
