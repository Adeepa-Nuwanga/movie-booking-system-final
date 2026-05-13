package com.moviebooking.service;

import com.moviebooking.model.User;
import com.moviebooking.model.UserRole;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthService {
    public static final String CURRENT_USER_SESSION_KEY = "currentUser";

    private static final List<User> USERS = seedUsers();

    private AuthService() {
    }

    public static User validateCredentials(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        String normalizedUsername = username.trim();
        for (User user : USERS) {
            if (user.getUsername().equalsIgnoreCase(normalizedUsername)
                    && user.getPassword().equals(password)) {
                return user;
            }
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

    private static List<User> seedUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Administrator", "admin", "admin123", UserRole.ADMIN));
        users.add(new User(2, "Demo User", "user", "user123", UserRole.USER));
        return Collections.unmodifiableList(users);
    }
}
