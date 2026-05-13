<%@ page import="com.moviebooking.model.Movie" %>
<%@ page import="java.util.List" %>
<%
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
    String selectedSort = (String) request.getAttribute("selectedSort");
    if (selectedSort == null) {
        selectedSort = "";
    }
%>
<%@ include file="fragments/header.jspf" %>

<main>
    <section class="movie-hero">
        <div class="container">
            <div class="movie-hero__content">
                <span class="section-kicker">Now Showing</span>
                <h1>Experience cinema at CineFlex</h1>
                <p>Browse the latest movies, compare ratings and ticket prices, then continue to showtimes when you are ready.</p>
                <a class="btn btn-danger" href="#movieGrid">Explore Movies</a>
            </div>
        </div>
    </section>

    <section class="container py-5" id="movieGrid">
        <div class="sort-panel">
            <div>
                <span class="section-kicker">Movie Lineup</span>
                <h2>Choose your next show</h2>
            </div>
            <form action="${pageContext.request.contextPath}/movies" method="get" class="sort-form">
                <label for="sort" class="form-label">Sort by</label>
                <select class="form-select" id="sort" name="sort" onchange="this.form.submit()">
                    <option value="" <%= selectedSort.isEmpty() ? "selected" : "" %>>Default</option>
                    <option value="rating" <%= "rating".equals(selectedSort) ? "selected" : "" %>>Rating</option>
                    <option value="price" <%= "price".equals(selectedSort) ? "selected" : "" %>>Price</option>
                    <option value="duration" <%= "duration".equals(selectedSort) ? "selected" : "" %>>Duration</option>
                    <option value="title" <%= "title".equals(selectedSort) ? "selected" : "" %>>Title</option>
                </select>
            </form>
        </div>

        <div class="row g-4">
            <% if (movies != null) {
                for (Movie movie : movies) { %>
                    <div class="col-12 col-sm-6 col-lg-4 col-xl-3">
                        <article class="movie-card">
                            <img src="<%= movie.getPosterUrl() %>" alt="<%= movie.getTitle() %> poster" class="movie-card__poster">
                            <div class="movie-card__body">
                                <div class="movie-card__meta">
                                    <span><%= movie.getGenre() %></span>
                                    <span><%= movie.getAgeRating() %></span>
                                </div>
                                <h3><%= movie.getTitle() %></h3>
                                <div class="movie-stats">
                                    <span>Rating <strong><%= movie.getRating() %></strong></span>
                                    <span><%= movie.getDurationMinutes() %> min</span>
                                </div>
                                <p class="movie-price">LKR <%= String.format("%.2f", movie.getPrice()) %></p>
                                <div class="movie-card__actions">
                                    <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/movie-details?id=<%= movie.getId() %>">View Details</a>
                                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/showtimes?movieId=<%= movie.getId() %>">Buy Tickets</a>
                                </div>
                            </div>
                        </article>
                    </div>
            <%  }
            } %>
        </div>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
