<%@ page import="com.moviebooking.model.Showtime" %>
<%@ page import="java.util.List" %>
<%
    List<Showtime> showtimes = (List<Showtime>) request.getAttribute("showtimes");
    String movieTitle = (String) request.getAttribute("movieTitle");
    String movieId = (String) request.getAttribute("movieId");
%>
<%@ include file="fragments/header.jspf" %>

<main>
    <section class="page-hero">
        <div class="container">
            <span class="section-kicker">Showtimes</span>
            <h1><%= movieTitle %></h1>
            <p>Select a cinema hall and time before choosing your seats.</p>
        </div>
    </section>

    <section class="container py-5">
        <% if (showtimes == null || showtimes.isEmpty()) { %>
            <div class="empty-panel">
                <span class="section-kicker">No Shows</span>
                <h2>No showtimes are available for this movie yet.</h2>
                <p>Please return to the movie listing and choose another movie.</p>
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/movies">Back to Movies</a>
            </div>
        <% } else { %>
            <div class="row g-4">
                <% for (Showtime showtime : showtimes) { %>
                    <div class="col-12 col-md-6 col-lg-4">
                        <article class="showtime-card">
                            <div>
                                <span class="showtime-card__movie">Movie <%= movieId %></span>
                                <h2><%= showtime.getCinemaHall() %></h2>
                            </div>
                            <div class="showtime-card__details">
                                <span><strong>Date</strong> <%= showtime.getDate() %></span>
                                <span><strong>Time</strong> <%= showtime.getTime() %></span>
                            </div>
                            <a class="btn btn-danger w-100" href="${pageContext.request.contextPath}/seats?showtimeId=<%= showtime.getId() %>">
                                Pick Seats
                            </a>
                        </article>
                    </div>
                <% } %>
            </div>
        <% } %>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
