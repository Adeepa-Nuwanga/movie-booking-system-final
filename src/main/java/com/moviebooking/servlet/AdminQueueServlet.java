package com.moviebooking.servlet;

import com.moviebooking.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/queue")
public class AdminQueueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("queueSize", BookingService.getQueueSize());
        request.setAttribute("pendingRequests", BookingService.getPendingRequests());
        request.setAttribute("processedRequests", BookingService.getProcessedRequests());
        request.setAttribute("lastProcessed", BookingService.getLastProcessed());
        request.getRequestDispatcher("/WEB-INF/views/admin-queue.jsp").forward(request, response);
    }
}
