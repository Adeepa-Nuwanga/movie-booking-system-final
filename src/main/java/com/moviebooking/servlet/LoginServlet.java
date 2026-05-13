package com.moviebooking.servlet;

import com.moviebooking.model.User;
import com.moviebooking.model.UserRole;
import com.moviebooking.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = AuthService.validateCredentials(username, password);

        if (user == null) {
            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(AuthService.CURRENT_USER_SESSION_KEY, user);

        String contextPath = request.getContextPath();
        if (user.getRole() == UserRole.ADMIN) {
            response.sendRedirect(contextPath + "/admin");
        } else {
            response.sendRedirect(contextPath + "/movies");
        }
    }
}
