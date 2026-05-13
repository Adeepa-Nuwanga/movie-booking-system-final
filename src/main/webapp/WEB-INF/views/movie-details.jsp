<%@ page import="com.moviebooking.model.Movie" %>
<%
    Movie movie = (Movie) request.getAttribute("movie");
%>
<%@ include file="fragments/header.jspf" %>

<main>
    <section class="details-hero" style="background-image: linear-gradient(90deg, rgba(0,0,0,0.94), rgba(0,0,0,0.52)), url('<%= movie.getBannerUrl() %>');">
        <div class="container">
            <div class="details-layout">
                <img src="<%= movie.getPosterUrl() %>" alt="<%= movie.getTitle() %> poster" class="details-poster">
                <div class="details-copy">
                    <span class="section-kicker"><%= movie.getGenre() %> | <%= movie.getAgeRating() %></span>
                    <h1><%= movie.getTitle() %></h1>
                    <p><%= movie.getDescription() %></p>
                    <div class="details-stats">
                        <span>Rating <strong><%= movie.getRating() %></strong></span>
                        <span><%= movie.getDurationMinutes() %> minutes</span>
                        <span>LKR <%= String.format("%.2f", movie.getPrice()) %></span>
                    </div>
                    <a class="btn btn-danger btn-lg" href="${pageContext.request.contextPath}/showtimes?movieId=<%= movie.getId() %>">Buy Tickets</a>
                </div>
            </div>
        </div>
    </section>

    <section class="container py-5">
        <div class="showtime-placeholder">
            <span class="section-kicker">Showtimes</span>
            <h2>Available showtimes coming soon</h2>
            <p>This area is reserved for the showtime and booking components.</p>
        </div>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
