package com.moviebooking.servlet;

import com.moviebooking.model.Movie;
import com.moviebooking.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/movies", "/admin/movies/update", "/admin/movies/delete"})
public class AdminMoviesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("movies", AdminService.getMovies());
        request.getRequestDispatcher("/WEB-INF/views/admin-movies.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/admin/movies/delete".equals(servletPath)) {
            AdminService.deleteMovie(request.getParameter("id"));
        } else if ("/admin/movies/update".equals(servletPath)) {
            AdminService.updateMovie(buildMovie(request, request.getParameter("id")));
        } else {
            AdminService.addMovie(buildMovie(request, AdminService.generateId("MOV")));
        }

        response.sendRedirect(request.getContextPath() + "/admin/movies");
    }

    private Movie buildMovie(HttpServletRequest request, String id) {
        return new Movie(
                id,
                value(request, "title"),
                value(request, "description"),
                value(request, "genre"),
                parseDouble(request.getParameter("rating")),
                parseInt(request.getParameter("durationMinutes")),
                parseDouble(request.getParameter("price")),
                value(request, "posterUrl"),
                value(request, "bannerUrl"),
                value(request, "ageRating")
        );
    }

    private String value(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null ? "" : value.trim();
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }
}
