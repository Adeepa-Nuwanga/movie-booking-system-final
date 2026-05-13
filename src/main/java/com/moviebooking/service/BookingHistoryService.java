package com.moviebooking.service;

import com.moviebooking.model.Booking;
import com.moviebooking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BookingHistoryService {
    private static final Map<String, List<Booking>> bookingsByUser = new HashMap<>();

    private BookingHistoryService() {
    }

    public static synchronized void saveConfirmedBooking(String userKey, Booking booking) {
        if (isBlank(userKey) || booking == null) {
            return;
        }

        List<Booking> bookings = bookingsByUser.get(userKey);
        if (bookings == null) {
            bookings = new ArrayList<>();
            bookingsByUser.put(userKey, bookings);
        }

        bookings.add(booking);
    }

    public static synchronized List<Booking> getBookingsForUser(String userKey) {
        List<Booking> bookings = bookingsByUser.get(userKey);
        if (bookings == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(bookings);
    }

    public static synchronized Booking getBookingById(String bookingId) {
        if (isBlank(bookingId)) {
            return null;
        }

        for (List<Booking> bookings : bookingsByUser.values()) {
            for (Booking booking : bookings) {
                if (booking.getBookingId().equals(bookingId)) {
                    return booking;
                }
            }
        }

        return null;
    }

    public static synchronized boolean cancelBooking(String bookingId) {
        return replaceBookingStatus(bookingId, true);
    }

    public static synchronized boolean removeBooking(String bookingId) {
        if (isBlank(bookingId)) {
            return false;
        }

        for (List<Booking> bookings : bookingsByUser.values()) {
            for (int i = 0; i < bookings.size(); i++) {
                if (bookings.get(i).getBookingId().equals(bookingId)) {
                    bookings.remove(i);
                    return true;
                }
            }
        }

        return false;
    }

    public static synchronized Booking createDemoBookingForUser(String userKey) {
        List<Booking> existingBookings = getBookingsForUser(userKey);
        if (!existingBookings.isEmpty()) {
            return existingBookings.get(0);
        }

        Booking booking = new Booking(
                generateId("BKG"),
                generateId("REQ"),
                userKey,
                "Demo Customer",
                userKey,
                "M001",
                "CineFlex Premiere",
                "ST001",
                "2026-05-20",
                "07:30 PM",
                "Hall A",
                Arrays.asList("C4", "C5"),
                3000.00,
                LocalDateTime.now(),
                BookingStatus.CONFIRMED
        );
        saveConfirmedBooking(userKey, booking);
        return booking;
    }

    private static boolean replaceBookingStatus(String bookingId, boolean cancel) {
        if (isBlank(bookingId) || !cancel) {
            return false;
        }

        for (List<Booking> bookings : bookingsByUser.values()) {
            for (int i = 0; i < bookings.size(); i++) {
                Booking booking = bookings.get(i);
                if (booking.getBookingId().equals(bookingId)) {
                    bookings.set(i, booking.cancelledCopy());
                    return true;
                }
            }
        }

        return false;
    }

    private static String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
