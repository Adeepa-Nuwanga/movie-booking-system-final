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
    private static final String BOOKINGS_FILE = "bookings.txt";
    // Data is cached in memory and persisted to TXT files for assignment requirements.
    private static final Map<String, List<Booking>> bookingsByUser = new HashMap<>();

    static {
        loadBookings();
    }

    private BookingHistoryService() {
    }

    public static synchronized void saveConfirmedBooking(String userKey, Booking booking) {
        if (isBlank(userKey) || booking == null) {
            return;
        }

        if (getBookingById(booking.getBookingId()) != null) {
            return;
        }

        List<Booking> bookings = bookingsByUser.get(userKey);
        if (bookings == null) {
            bookings = new ArrayList<>();
            bookingsByUser.put(userKey, bookings);
        }

        bookings.add(booking);
        FileStorageService.appendLine(BOOKINGS_FILE, toFileLine(booking));
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
                    saveBookings();
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
                    saveBookings();
                    return true;
                }
            }
        }

        return false;
    }

    private static void loadBookings() {
        List<String> lines = FileStorageService.readLines(BOOKINGS_FILE);
        for (String line : lines) {
            if (isBlank(line)) {
                continue;
            }

            String[] parts = line.split(",", -1);
            if (parts.length < 7) {
                continue;
            }

            try {
                String bookingId = parts[0].trim();
                String userKey = parts[1].trim();
                String movieId = parts[2].trim();
                String showtimeId = parts[3].trim();
                List<String> seats = parseSeats(parts[4]);
                BookingStatus status = BookingStatus.valueOf(parts[5].trim().toUpperCase());
                double total = Double.parseDouble(parts[6].trim());

                Booking booking = new Booking(
                        bookingId,
                        "REQ-" + bookingId,
                        userKey,
                        isBlank(userKey) ? "CineFlex Customer" : userKey,
                        userKey,
                        movieId,
                        movieId,
                        showtimeId,
                        "TBD",
                        "TBD",
                        "Main Hall",
                        seats,
                        total,
                        LocalDateTime.now(),
                        status
                );
                addLoadedBooking(userKey, booking);
            } catch (IllegalArgumentException e) {
                // Skip malformed assignment demo rows and keep booking history available.
            }
        }
    }

    private static void addLoadedBooking(String userKey, Booking booking) {
        List<Booking> bookings = bookingsByUser.get(userKey);
        if (bookings == null) {
            bookings = new ArrayList<>();
            bookingsByUser.put(userKey, bookings);
        }
        bookings.add(booking);
    }

    private static void saveBookings() {
        List<String> lines = new ArrayList<>();
        for (List<Booking> bookings : bookingsByUser.values()) {
            for (Booking booking : bookings) {
                lines.add(toFileLine(booking));
            }
        }
        FileStorageService.writeLines(BOOKINGS_FILE, lines);
    }

    private static String toFileLine(Booking booking) {
        return clean(booking.getBookingId())
                + "," + clean(booking.getUserKey())
                + "," + clean(booking.getMovieId())
                + "," + clean(booking.getShowtimeId())
                + "," + clean(String.join("|", booking.getSeats()))
                + "," + booking.getStatus().name()
                + "," + booking.getTotalPrice();
    }

    private static List<String> parseSeats(String seatsValue) {
        List<String> seats = new ArrayList<>();
        if (isBlank(seatsValue)) {
            return seats;
        }

        String[] parts = seatsValue.split("\\|");
        for (String seat : parts) {
            if (!isBlank(seat)) {
                seats.add(seat.trim());
            }
        }
        return seats;
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim().replace(",", " ").replace("\r", " ").replace("\n", " ");
    }

    private static String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
