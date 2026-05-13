package com.moviebooking.servlet;

import com.moviebooking.model.BookingRequest;
import com.moviebooking.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<String> selectedSeats = getSelectedSeats(session);
        String showtimeId = getSessionString(session, "selectedShowtimeId");

        if (showtimeId == null || selectedSeats.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/movies");
            return;
        }

        setCheckoutSummary(request, session, showtimeId, selectedSeats);
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<String> selectedSeats = getSelectedSeats(session);
        String showtimeId = getSessionString(session, "selectedShowtimeId");

        if (showtimeId == null || selectedSeats.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/movies");
            return;
        }

        String customerName = defaultIfBlank(request.getParameter("customerName"), "Guest Customer");
        String customerEmail = defaultIfBlank(request.getParameter("customerEmail"), "guest@example.com");
        String movieId = defaultIfBlank(getSessionString(session, "selectedMovieId"), "N/A");
        double totalPrice = selectedSeats.size() * BookingService.getDefaultTicketPrice();

        BookingRequest bookingRequest = new BookingRequest(
                BookingService.generateRequestId(),
                customerName,
                customerEmail,
                movieId,
                showtimeId,
                selectedSeats,
                totalPrice
        );

        BookingService.enqueueBooking(bookingRequest);
        BookingService.processNext();

        response.sendRedirect(request.getContextPath() + "/booking-result?requestId=" + bookingRequest.getRequestId());
    }

    private void setCheckoutSummary(HttpServletRequest request, HttpSession session,
                                    String showtimeId, List<String> selectedSeats) {
        double ticketPrice = BookingService.getDefaultTicketPrice();
        request.setAttribute("movieId", defaultIfBlank(getSessionString(session, "selectedMovieId"), "N/A"));
        request.setAttribute("showtimeId", showtimeId);
        request.setAttribute("selectedSeats", selectedSeats);
        request.setAttribute("ticketPrice", ticketPrice);
        request.setAttribute("totalPrice", selectedSeats.size() * ticketPrice);
        request.setAttribute("customerName", defaultIfBlank(getSessionString(session, "customerName"), ""));
        request.setAttribute("customerEmail", defaultIfBlank(getSessionString(session, "customerEmail"), ""));
    }

    private List<String> getSelectedSeats(HttpSession session) {
        List<String> selectedSeats = new ArrayList<>();
        if (session == null) {
            return selectedSeats;
        }

        Object seats = session.getAttribute("selectedSeats");
        if (seats instanceof List<?>) {
            for (Object seat : (List<?>) seats) {
                if (seat != null) {
                    selectedSeats.add(seat.toString());
                }
            }
        } else if (seats instanceof String[]) {
            for (String seat : (String[]) seats) {
                selectedSeats.add(seat);
            }
        }

        return selectedSeats;
    }

    private String getSessionString(HttpSession session, String key) {
        if (session == null || session.getAttribute(key) == null) {
            return null;
        }

        return session.getAttribute(key).toString();
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }

        return value.trim();
    }
}
