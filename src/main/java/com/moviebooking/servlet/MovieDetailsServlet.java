package com.moviebooking.servlet;

import com.moviebooking.model.Movie;
import com.moviebooking.service.MovieService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/movie-details")
public class MovieDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Movie movie = MovieService.getMovieById(request.getParameter("id"));

        if (movie == null) {
            response.sendRedirect(request.getContextPath() + "/movies");
            return;
        }

        request.setAttribute("movie", movie);
        request.getRequestDispatcher("/WEB-INF/views/movie-details.jsp").forward(request, response);
    }
}
