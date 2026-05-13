package com.moviebooking.service;

import com.moviebooking.model.SeatMap;
import com.moviebooking.model.Showtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowtimeService {
    private static final List<Showtime> SHOWTIMES = new ArrayList<>();

    static {
        seedShowtimes();
    }

    private ShowtimeService() {
    }

    public static synchronized List<Showtime> getShowtimesByMovieId(String movieId) {
        List<Showtime> matchingShowtimes = new ArrayList<>();
        if (movieId == null) {
            return matchingShowtimes;
        }

        for (Showtime showtime : SHOWTIMES) {
            if (showtime.getMovieId().equalsIgnoreCase(movieId.trim())) {
                matchingShowtimes.add(showtime);
            }
        }

        return matchingShowtimes;
    }

    public static synchronized Showtime getShowtimeById(String showtimeId) {
        if (showtimeId == null) {
            return null;
        }

        for (Showtime showtime : SHOWTIMES) {
            if (showtime.getId().equalsIgnoreCase(showtimeId.trim())) {
                return showtime;
            }
        }

        return null;
    }

    public static synchronized SeatMap getSeatMap(String showtimeId) {
        Showtime showtime = getShowtimeById(showtimeId);
        if (showtime == null) {
            return null;
        }

        return showtime.getSeatMap();
    }

    private static void seedShowtimes() {
        SHOWTIMES.add(createShowtime("s1", "m1", "Hall A", "2026-05-14", "10:30 AM", Arrays.asList("A1", "A2", "C5")));
        SHOWTIMES.add(createShowtime("s2", "m1", "Hall B", "2026-05-14", "02:15 PM", Arrays.asList("B3", "B4", "D7")));
        SHOWTIMES.add(createShowtime("s3", "m1", "Hall A", "2026-05-14", "07:00 PM", Arrays.asList("E5", "E6", "F1")));
        SHOWTIMES.add(createShowtime("s4", "m2", "Hall C", "2026-05-15", "11:00 AM", Arrays.asList("A4", "A5", "B6")));
        SHOWTIMES.add(createShowtime("s5", "m2", "Hall C", "2026-05-15", "04:30 PM", Arrays.asList("C2", "C3", "D4")));
        SHOWTIMES.add(createShowtime("s6", "m3", "Hall B", "2026-05-16", "06:45 PM", Arrays.asList("A7", "B7", "C7")));
    }

    private static Showtime createShowtime(String id, String movieId, String cinemaHall,
                                           String date, String time, List<String> bookedSeats) {
        SeatMap seatMap = new SeatMap(6, 8);
        seatMap.bookSeats(bookedSeats);
        return new Showtime(id, movieId, cinemaHall, date, time, seatMap);
    }
}
