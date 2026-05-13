package com.moviebooking.servlet;

import com.moviebooking.model.Booking;
import com.moviebooking.service.BookingHistoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

@WebServlet("/my-bookings")
public class MyBookingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userKey = resolveUserKey(request.getSession(false));
        List<Booking> bookings = BookingHistoryService.getBookingsForUser(userKey);
        if (bookings.isEmpty()) {
            BookingHistoryService.createDemoBookingForUser(userKey);
            bookings = BookingHistoryService.getBookingsForUser(userKey);
        }

        request.setAttribute("userKey", userKey);
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/WEB-INF/views/my-bookings.jsp").forward(request, response);
    }

    private String resolveUserKey(HttpSession session) {
        if (session == null) {
            return "demo@cineflex.local";
        }

        Object currentUser = session.getAttribute("currentUser");
        String currentUserKey = readUserProperty(currentUser, "getEmail");
        if (isBlank(currentUserKey)) {
            currentUserKey = readUserProperty(currentUser, "getUsername");
        }
        if (!isBlank(currentUserKey)) {
            return currentUserKey;
        }

        Object customerEmail = session.getAttribute("customerEmail");
        if (customerEmail != null && !isBlank(customerEmail.toString())) {
            return customerEmail.toString();
        }

        return "demo@cineflex.local";
    }

    private String readUserProperty(Object user, String methodName) {
        if (user == null) {
            return "";
        }

        try {
            Method method = user.getClass().getMethod(methodName);
            Object value = method.invoke(user);
            return value == null ? "" : value.toString();
        } catch (ReflectiveOperationException exception) {
            return "";
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
