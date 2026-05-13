package com.moviebooking.servlet;

import com.moviebooking.model.Showtime;
import com.moviebooking.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import com.moviebooking.model.SeatMap;

@WebServlet(urlPatterns = {"/admin/showtimes", "/admin/showtimes/update", "/admin/showtimes/delete"})
public class AdminShowtimesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("showtimes", AdminService.getShowtimes());
        request.setAttribute("movies", AdminService.getMovies());
        request.getRequestDispatcher("/WEB-INF/views/admin-showtimes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/admin/showtimes/delete".equals(servletPath)) {
            AdminService.deleteShowtime(request.getParameter("id"));
        } else if ("/admin/showtimes/update".equals(servletPath)) {
            AdminService.updateShowtime(buildShowtime(request, request.getParameter("id")));
        } else {
            AdminService.addShowtime(buildShowtime(request, AdminService.generateId("SHW")));
        }

        response.sendRedirect(request.getContextPath() + "/admin/showtimes");
    }

    private Showtime buildShowtime(HttpServletRequest request, String id) {
        return new Showtime(
                id,
                value(request, "movieId"),
                value(request, "cinemaHall"),
                value(request, "date"),
                value(request, "time"),
                new SeatMap(6, 8)
        );
    }

    private String value(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null ? "" : value.trim();
    }
}
