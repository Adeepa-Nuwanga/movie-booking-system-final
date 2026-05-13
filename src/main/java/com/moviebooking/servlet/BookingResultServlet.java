package com.moviebooking.servlet;

import com.moviebooking.model.BookingRequest;
import com.moviebooking.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/booking-result")
public class BookingResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookingRequest bookingRequest = BookingService.findRequestById(request.getParameter("requestId"));
        if (bookingRequest == null) {
            response.sendRedirect(request.getContextPath() + "/movies");
            return;
        }

        request.setAttribute("bookingRequest", bookingRequest);
        request.getRequestDispatcher("/WEB-INF/views/booking-result.jsp").forward(request, response);
    }
}
