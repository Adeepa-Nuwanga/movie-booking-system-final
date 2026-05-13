package com.moviebooking.servlet;

import com.moviebooking.model.SeatMap;
import com.moviebooking.model.Showtime;
import com.moviebooking.service.ShowtimeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/seats")
public class SeatSelectionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Showtime showtime = ShowtimeService.getShowtimeById(request.getParameter("showtimeId"));
        if (showtime == null) {
            response.sendRedirect(request.getContextPath() + "/movies");
            return;
        }

        request.setAttribute("showtime", showtime);
        request.setAttribute("seatMap", showtime.getSeatMap());
        request.setAttribute("ticketPrice", 1500.00);
        request.getRequestDispatcher("/WEB-INF/views/seat-selection.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String showtimeId = request.getParameter("showtimeId");
        String[] selectedSeatValues = request.getParameterValues("selectedSeats");
        Showtime showtime = ShowtimeService.getShowtimeById(showtimeId);

        if (showtime == null) {
            response.sendRedirect(request.getContextPath() + "/movies");
            return;
        }

        if (selectedSeatValues == null || selectedSeatValues.length == 0) {
            request.setAttribute("errorMessage", "Please select at least one seat.");
            request.setAttribute("showtime", showtime);
            request.setAttribute("seatMap", showtime.getSeatMap());
            request.setAttribute("ticketPrice", 1500.00);
            request.getRequestDispatcher("/WEB-INF/views/seat-selection.jsp").forward(request, response);
            return;
        }

        List<String> selectedSeats = Arrays.asList(selectedSeatValues);
        SeatMap seatMap = ShowtimeService.getSeatMap(showtimeId);
        if (seatMap == null || !seatMap.holdSeats(selectedSeats)) {
            request.setAttribute("errorMessage", "One or more selected seats are no longer available.");
            request.setAttribute("showtime", showtime);
            request.setAttribute("seatMap", showtime.getSeatMap());
            request.setAttribute("ticketPrice", 1500.00);
            request.getRequestDispatcher("/WEB-INF/views/seat-selection.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("selectedShowtimeId", showtimeId);
        session.setAttribute("selectedSeats", selectedSeats);
        response.sendRedirect(request.getContextPath() + "/checkout");
    }
}
