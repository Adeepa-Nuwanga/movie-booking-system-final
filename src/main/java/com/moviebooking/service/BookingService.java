package com.moviebooking.service;

import com.moviebooking.model.Booking;
import com.moviebooking.model.BookingRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public class BookingService {
    private static final double DEFAULT_TICKET_PRICE = 1500.00;
    private static final Queue<BookingRequest> bookingQueue = new LinkedList<>();
    private static final List<BookingRequest> processedRequests = new ArrayList<>();
    private static final List<Booking> confirmedBookings = new ArrayList<>();
    private static final Map<String, Set<String>> confirmedSeatsByShowtime = new HashMap<>();
    private static BookingRequest lastProcessed;

    private BookingService() {
    }

    public static synchronized void enqueueBooking(BookingRequest request) {
        if (request != null) {
            bookingQueue.offer(request);
        }
    }

    public static synchronized BookingRequest processNext() {
        BookingRequest request = bookingQueue.poll();
        if (request == null) {
            return null;
        }

        if (validateSeats(request.getShowtimeId(), request.getSelectedSeats())) {
            markSeatsConfirmed(request.getShowtimeId(), request.getSelectedSeats());
            request.confirm();
            Booking booking = new Booking(generateBookingId(), request);
            confirmedBookings.add(booking);
            BookingHistoryService.saveConfirmedBooking(booking.getUserKey(), booking);
        } else {
            request.reject("One or more selected seats are no longer available.");
        }

        processedRequests.add(request);
        lastProcessed = request;
        return request;
    }

    public static synchronized boolean validateSeats(String showtimeId, List<String> selectedSeats) {
        if (isBlank(showtimeId) || selectedSeats == null || selectedSeats.isEmpty()) {
            return false;
        }

        Set<String> confirmedSeats = confirmedSeatsByShowtime.get(showtimeId);
        if (confirmedSeats == null) {
            return true;
        }

        for (String seat : selectedSeats) {
            if (confirmedSeats.contains(normalizeSeat(seat))) {
                return false;
            }
        }

        return true;
    }

    public static synchronized int getQueueSize() {
        return bookingQueue.size();
    }

    public static synchronized List<BookingRequest> getPendingRequests() {
        return new ArrayList<>(bookingQueue);
    }

    public static synchronized List<BookingRequest> getProcessedRequests() {
        return new ArrayList<>(processedRequests);
    }

    public static synchronized BookingRequest getLastProcessed() {
        return lastProcessed;
    }

    public static synchronized BookingRequest findRequestById(String requestId) {
        if (requestId == null) {
            return null;
        }

        for (BookingRequest request : processedRequests) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }

        for (BookingRequest request : bookingQueue) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }

        return null;
    }

    public static String generateRequestId() {
        return "REQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static double getDefaultTicketPrice() {
        return DEFAULT_TICKET_PRICE;
    }

    private static void markSeatsConfirmed(String showtimeId, List<String> selectedSeats) {
        Set<String> confirmedSeats = confirmedSeatsByShowtime.get(showtimeId);
        if (confirmedSeats == null) {
            confirmedSeats = new HashSet<>();
            confirmedSeatsByShowtime.put(showtimeId, confirmedSeats);
        }

        for (String seat : selectedSeats) {
            confirmedSeats.add(normalizeSeat(seat));
        }
    }

    private static String generateBookingId() {
        return "BKG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private static String normalizeSeat(String seat) {
        return seat == null ? "" : seat.trim().toUpperCase();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
