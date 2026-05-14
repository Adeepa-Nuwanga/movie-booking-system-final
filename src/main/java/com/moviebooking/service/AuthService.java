package com.moviebooking.service;

import com.moviebooking.model.User;
import com.moviebooking.model.UserRole;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class AuthService {
    public static final String CURRENT_USER_SESSION_KEY = "currentUser";

    private static final String USERS_FILE = "users.txt";
    // Data is cached in memory and persisted to TXT files for assignment requirements.
    private static final List<User> USERS = new ArrayList<>();
    private static int nextUserId = 1;

    static {
        loadUsers();
        if (USERS.isEmpty()) {
            seedUsers();
            saveUsers();
        }
    }

    private AuthService() {
    }

    public static synchronized User registerUser(String name, String username, String password, String email) {
        String normalizedName = normalize(name);
        String normalizedUsername = normalize(username);
        String normalizedEmail = normalize(email);

        if (isBlank(normalizedName) || isBlank(normalizedUsername) || isBlank(password)) {
            return null;
        }

        if (findByUsername(normalizedUsername) != null || emailExists(normalizedEmail)) {
            return null;
        }

        User user = new User(nextUserId++, normalizedName, normalizedUsername, normalizedEmail, password, UserRole.USER);
        USERS.add(user);
        FileStorageService.appendLine(USERS_FILE, toFileLine(user));
        return user;
    }

    public static synchronized User findByUsername(String username) {
        if (username == null) {
            return null;
        }

        String normalizedUsername = normalize(username);
        for (User user : USERS) {
            if (user.getUsername().equalsIgnoreCase(normalizedUsername)) {
                return user;
            }
        }

        return null;
    }

    public static synchronized boolean emailExists(String email) {
        if (isBlank(email)) {
            return false;
        }

        String normalizedEmail = normalize(email);
        for (User user : USERS) {
            if (!isBlank(user.getEmail()) && user.getEmail().equalsIgnoreCase(normalizedEmail)) {
                return true;
            }
        }

        return false;
    }

    public static synchronized User validateCredentials(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        User user = findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    public static User getCurrentUser(HttpSession session) {
        if (session == null) {
            return null;
        }

        Object user = session.getAttribute(CURRENT_USER_SESSION_KEY);
        if (user instanceof User) {
            return (User) user;
        }

        return null;
    }

    public static boolean isLoggedIn(HttpSession session) {
        return getCurrentUser(session) != null;
    }

    public static boolean isAdmin(HttpSession session) {
        User user = getCurrentUser(session);
        return user != null && user.getRole() == UserRole.ADMIN;
    }

    public static void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    private static void seedUsers() {
        USERS.add(new User(nextUserId++, "Administrator", "admin", "admin@cineflex.local", "admin123", UserRole.ADMIN));
        USERS.add(new User(nextUserId++, "Demo User", "user", "user@cineflex.local", "user123", UserRole.USER));
    }

    private static void loadUsers() {
        List<String> lines = FileStorageService.readLines(USERS_FILE);
        int highestId = 0;

        for (String line : lines) {
            if (isBlank(line)) {
                continue;
            }

            String[] parts = line.split(",", -1);
            if (parts.length < 6) {
                continue;
            }

            try {
                int id = Integer.parseInt(parts[0].trim());
                UserRole role = UserRole.valueOf(parts[5].trim().toUpperCase());
                USERS.add(new User(id, parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4], role));
                if (id > highestId) {
                    highestId = id;
                }
            } catch (IllegalArgumentException e) {
                // Skip malformed assignment demo rows and keep the app running.
            }
        }

        nextUserId = highestId + 1;
    }

    private static void saveUsers() {
        List<String> lines = new ArrayList<>();
        for (User user : USERS) {
            lines.add(toFileLine(user));
        }
        FileStorageService.writeLines(USERS_FILE, lines);
    }

    private static String toFileLine(User user) {
        return user.getId()
                + "," + clean(user.getName())
                + "," + clean(user.getUsername())
                + "," + clean(user.getEmail())
                + "," + clean(user.getPassword())
                + "," + user.getRole().name();
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private static String clean(String value) {
        return normalize(value).replace(",", " ").replace("\r", " ").replace("\n", " ");
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
