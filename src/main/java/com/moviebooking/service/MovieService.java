package com.moviebooking.service;

import com.moviebooking.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private static final ArrayList<Movie> MOVIES = new ArrayList<>();

    static {
        seedMovies();
    }

    private MovieService() {
    }

    public static synchronized List<Movie> getAllMovies() {
        return new ArrayList<>(MOVIES);
    }

    public static synchronized Movie getMovieById(String id) {
        if (id == null) {
            return null;
        }

        for (Movie movie : MOVIES) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }

        return null;
    }

    public static synchronized void addMovie(Movie movie) {
        if (movie != null && getMovieById(movie.getId()) == null) {
            MOVIES.add(movie);
        }
    }

    public static synchronized void updateMovie(Movie movie) {
        if (movie == null || movie.getId() == null) {
            return;
        }

        for (int i = 0; i < MOVIES.size(); i++) {
            if (MOVIES.get(i).getId().equals(movie.getId())) {
                MOVIES.set(i, movie);
                return;
            }
        }
    }

    public static synchronized void deleteMovie(String id) {
        if (id == null) {
            return;
        }

        for (int i = 0; i < MOVIES.size(); i++) {
            if (MOVIES.get(i).getId().equals(id)) {
                MOVIES.remove(i);
                return;
            }
        }
    }

    public static synchronized List<Movie> getSortedMovies(String sortBy) {
        ArrayList<Movie> sortedMovies = new ArrayList<>(MOVIES);
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return sortedMovies;
        }

        insertionSort(sortedMovies, sortBy.trim().toLowerCase());
        return sortedMovies;
    }

    private static void insertionSort(ArrayList<Movie> movies, String sortBy) {
        for (int i = 1; i < movies.size(); i++) {
            Movie current = movies.get(i);
            int j = i - 1;

            while (j >= 0 && shouldMoveAfterCurrent(movies.get(j), current, sortBy)) {
                movies.set(j + 1, movies.get(j));
                j--;
            }

            movies.set(j + 1, current);
        }
    }

    private static boolean shouldMoveAfterCurrent(Movie existing, Movie current, String sortBy) {
        switch (sortBy) {
            case "rating":
                return existing.getRating() < current.getRating();
            case "price":
                return existing.getPrice() > current.getPrice();
            case "duration":
                return existing.getDurationMinutes() > current.getDurationMinutes();
            case "title":
                return existing.getTitle().compareToIgnoreCase(current.getTitle()) > 0;
            default:
                return false;
        }
    }

    private static void seedMovies() {
        MOVIES.add(new Movie(
                "m1",
                "Dune: Part Two",
                "Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family.",
                "Sci-Fi / Adventure",
                8.6,
                166,
                1800.00,
                "https://image.tmdb.org/t/p/w500/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg",
                "https://image.tmdb.org/t/p/original/xOMo8BRK7PfcJv9JCnx7s5hj0PX.jpg",
                "PG-13"
        ));
        MOVIES.add(new Movie(
                "m2",
                "Inside Out 2",
                "Riley enters her teenage years with a new wave of emotions joining Joy, Sadness, Anger, Fear, and Disgust.",
                "Animation / Family",
                7.7,
                96,
                1200.00,
                "https://image.tmdb.org/t/p/w500/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg",
                "https://image.tmdb.org/t/p/original/stKGOm8UyhuLPR9sZLjs5AkmncA.jpg",
                "PG"
        ));
        MOVIES.add(new Movie(
                "m3",
                "The Batman",
                "Batman ventures into Gotham City's underworld when a sadistic killer leaves behind a trail of cryptic clues.",
                "Action / Crime",
                7.8,
                176,
                1500.00,
                "https://image.tmdb.org/t/p/w500/74xTEgt7R36Fpooo50r9T25onhq.jpg",
                "https://image.tmdb.org/t/p/original/b0PlSFdDwbyK0cf5RxwDpaOJQvQ.jpg",
                "PG-13"
        ));
        MOVIES.add(new Movie(
                "m4",
                "Godzilla x Kong: The New Empire",
                "Two ancient titans face a colossal threat hidden within the world, challenging their existence and ours.",
                "Action / Fantasy",
                6.5,
                115,
                1600.00,
                "https://image.tmdb.org/t/p/w500/z1p34vh7dEOnLDmyCrlUVLuoDzd.jpg",
                "https://image.tmdb.org/t/p/original/j3Z3XktmWB1VhsS8iXNcrR86PXi.jpg",
                "PG-13"
        ));
        MOVIES.add(new Movie(
                "m5",
                "Oppenheimer",
                "The story of J. Robert Oppenheimer and the creation of the atomic bomb during World War II.",
                "Biography / Drama",
                8.3,
                181,
                1700.00,
                "https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
                "https://image.tmdb.org/t/p/original/fm6KqXpk3M2HVveHwCrBSSBaO0V.jpg",
                "R"
        ));
        MOVIES.add(new Movie(
                "m6",
                "Bad Boys: Ride or Die",
                "Miami detectives Mike Lowrey and Marcus Burnett return for another fast, loud, and dangerous mission.",
                "Action / Comedy",
                6.9,
                115,
                1400.00,
                "https://image.tmdb.org/t/p/w500/oGythE98MYleE6mZlGs5oBGkux1.jpg",
                "https://image.tmdb.org/t/p/original/nP6RliHjxsz4irTKsxe8FRhKZYl.jpg",
                "R"
        ));
    }
}
