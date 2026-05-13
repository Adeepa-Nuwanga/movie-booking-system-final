package com.moviebooking.servlet;

import com.moviebooking.model.Booking;
import com.moviebooking.service.BookingHistoryService;
import com.moviebooking.service.ReceiptBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ticket")
public class TicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Booking booking = BookingHistoryService.getBookingById(request.getParameter("bookingId"));
        if (booking == null) {
            response.sendRedirect(request.getContextPath() + "/my-bookings");
            return;
        }

        request.setAttribute("receipt", ReceiptBuilder.build(booking));
        request.getRequestDispatcher("/WEB-INF/views/ticket.jsp").forward(request, response);
    }
}
