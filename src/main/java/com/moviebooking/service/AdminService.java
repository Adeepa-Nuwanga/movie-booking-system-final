package com.moviebooking.service;

import com.moviebooking.model.AdminBookingSummary;
import com.moviebooking.model.Movie;
import com.moviebooking.model.Showtime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.moviebooking.model.SeatMap;

public class AdminService {
    private static final List<Showtime> showtimes = new ArrayList<>();
    private static final List<AdminBookingSummary> recentBookings = new ArrayList<>();
    private static int queueLength = 2;

    static {
        seedShowtimes();
        seedBookings();
    }

    private AdminService() {
    }

    public static synchronized int getTotalMovies() {
        return MovieService.getAllMovies().size();
    }

    public static synchronized int getTotalShowtimes() {
        return showtimes.size();
    }

    public static synchronized int getQueueLength() {
        return queueLength;
    }

    public static synchronized int getRecentBookingsCount() {
        return recentBookings.size();
    }

    public static synchronized List<Movie> getMovies() {
        return MovieService.getAllMovies();
    }

    public static synchronized Movie getMovieById(String id) {
        return MovieService.getMovieById(id);
    }

    public static synchronized void addMovie(Movie movie) {
        MovieService.addMovie(movie);
    }

    public static synchronized void updateMovie(Movie updatedMovie) {
        MovieService.updateMovie(updatedMovie);
    }

    public static synchronized void deleteMovie(String id) {
        MovieService.deleteMovie(id);
    }

    public static synchronized List<Showtime> getShowtimes() {
        return new ArrayList<>(showtimes);
    }

    public static synchronized void addShowtime(Showtime showtime) {
        if (showtime != null) {
            showtimes.add(showtime);
        }
    }

    public static synchronized void updateShowtime(Showtime updatedShowtime) {
        if (updatedShowtime == null) {
            return;
        }
        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getId().equals(updatedShowtime.getId())) {
                showtimes.set(i, updatedShowtime);
                return;
            }
        }
    }

    public static synchronized void deleteShowtime(String id) {
        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getId().equals(id)) {
                showtimes.remove(i);
                return;
            }
        }
    }

    public static synchronized List<AdminBookingSummary> getRecentBookings() {
        return new ArrayList<>(recentBookings);
    }

    public static String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private static void seedShowtimes() {
        showtimes.add(new Showtime("s1", "m1", "Hall A", "2026-05-14", "10:30 AM", new SeatMap(6, 8)));
        showtimes.add(new Showtime("s2", "m1", "Hall B", "2026-05-14", "07:00 PM", new SeatMap(6, 8)));
        showtimes.add(new Showtime("s3", "m2", "Hall C", "2026-05-15", "04:30 PM", new SeatMap(6, 8)));
    }

    private static void seedBookings() {
        recentBookings.add(new AdminBookingSummary("BKG-1001", "Demo Customer", "Dune: Part Two", "C4, C5", "CONFIRMED", 3600.00));
        recentBookings.add(new AdminBookingSummary("BKG-1002", "Admin Preview", "Inside Out 2", "A1", "PENDING", 1200.00));
    }
}
