package com.moviebooking.servlet;

import com.moviebooking.model.Showtime;
import com.moviebooking.service.ShowtimeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/showtimes")
public class ShowtimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String movieId = request.getParameter("movieId");
        List<Showtime> showtimes = ShowtimeService.getShowtimesByMovieId(movieId);

        request.setAttribute("movieId", movieId);
        request.setAttribute("movieTitle", movieId == null || movieId.trim().isEmpty() ? "Selected Movie" : "Movie " + movieId);
        request.setAttribute("showtimes", showtimes);
        request.getRequestDispatcher("/WEB-INF/views/showtimes.jsp").forward(request, response);
    }
}
