package com.moviebooking.servlet;

import com.moviebooking.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        String errorMessage = validateSignup(name, username, email, password, confirmPassword);
        if (errorMessage != null) {
            forwardWithError(request, response, errorMessage, name, username, email);
            return;
        }

        if (AuthService.findByUsername(username) != null) {
            forwardWithError(request, response, "Username is already taken.", name, username, email);
            return;
        }

        if (AuthService.emailExists(email)) {
            forwardWithError(request, response, "Email is already registered.", name, username, email);
            return;
        }

        if (AuthService.registerUser(name, username, password, email) == null) {
            forwardWithError(request, response, "Unable to create account. Please check your details.", name, username, email);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/login?registered=true");
    }

    private String validateSignup(String name, String username, String email, String password, String confirmPassword) {
        if (isBlank(name)) {
            return "Full name is required.";
        }

        if (isBlank(username)) {
            return "Username is required.";
        }

        if (isBlank(password)) {
            return "Password is required.";
        }

        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }

        return null;
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response,
                                  String errorMessage, String name, String username, String email)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("nameValue", safeValue(name));
        request.setAttribute("usernameValue", safeValue(username));
        request.setAttribute("emailValue", safeValue(email));
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String safeValue(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
