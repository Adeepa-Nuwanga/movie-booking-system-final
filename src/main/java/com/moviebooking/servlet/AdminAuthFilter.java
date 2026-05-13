package com.moviebooking.servlet;

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
import java.lang.reflect.Method;

@WebFilter(urlPatterns = {"/admin", "/admin/*"})
public class AdminAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isAdmin(httpRequest.getSession(false))) {
            chain.doFilter(request, response);
            return;
        }

        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        request.getRequestDispatcher("/WEB-INF/views/admin-forbidden.jsp").forward(request, response);
    }

    private boolean isAdmin(HttpSession session) {
        if (session == null) {
            return false;
        }

        Object role = session.getAttribute("role");
        if (role != null && "ADMIN".equalsIgnoreCase(role.toString())) {
            return true;
        }

        Object currentUser = session.getAttribute("currentUser");
        if (currentUser == null) {
            return false;
        }

        try {
            Method getRole = currentUser.getClass().getMethod("getRole");
            Object userRole = getRole.invoke(currentUser);
            return userRole != null && "ADMIN".equalsIgnoreCase(userRole.toString());
        } catch (ReflectiveOperationException exception) {
            return false;
        }
    }
}
