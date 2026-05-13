package com.moviebooking.servlet;

import com.moviebooking.service.AuthService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin", "/admin/*"})
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (!AuthService.isLoggedIn(session)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        if (!AuthService.isAdmin(session)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            request.setAttribute("errorMessage", "You are not authorized to access the admin area.");
            request.getRequestDispatcher("/WEB-INF/views/forbidden.jsp").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}