package com.moviebooking.service;

import com.moviebooking.model.User;
import com.moviebooking.model.UserRole;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class AuthService {
    public static final String CURRENT_USER_SESSION_KEY = "currentUser";

    // Users are stored in memory for this OOP assignment demo. Data resets when the server restarts.
    private static final List<User> USERS = new ArrayList<>();
    private static int nextUserId = 1;

    static {
        seedUsers();
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

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
