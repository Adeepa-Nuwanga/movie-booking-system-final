package com.moviebooking.servlet;

import com.moviebooking.model.Movie;
import com.moviebooking.service.MovieService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/movies")
public class MovieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sortBy = request.getParameter("sort");
        List<Movie> movies = MovieService.getSortedMovies(sortBy);

        request.setAttribute("movies", movies);
        request.setAttribute("selectedSort", sortBy == null ? "" : sortBy);
        request.getRequestDispatcher("/WEB-INF/views/movies.jsp").forward(request, response);
    }
}
