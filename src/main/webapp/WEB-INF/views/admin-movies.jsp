<%@ page import="com.moviebooking.model.Movie" %>
<%@ page import="java.util.List" %>
<%
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
%>
<%@ include file="fragments/header.jspf" %>
<main class="container py-5">
    <div class="admin-layout">
        <aside class="admin-sidebar">
            <a href="${pageContext.request.contextPath}/admin">Dashboard</a>
            <a class="active" href="${pageContext.request.contextPath}/admin/movies">Movies</a>
            <a href="${pageContext.request.contextPath}/admin/showtimes">Showtimes</a>
            <a href="${pageContext.request.contextPath}/admin/bookings">Bookings</a>
            <a href="${pageContext.request.contextPath}/admin/bookings">Queue</a>
        </aside>
        <section class="admin-panel">
            <span class="section-kicker">Catalog Control</span>
            <h1>Movies</h1>

            <form class="admin-form" action="${pageContext.request.contextPath}/admin/movies" method="post">
                <h2>Add Movie</h2>
                <div class="row g-3">
                    <div class="col-md-6"><input class="form-control" name="title" placeholder="Title" required></div>
                    <div class="col-md-6"><input class="form-control" name="genre" placeholder="Genre" required></div>
                    <div class="col-md-12"><textarea class="form-control" name="description" placeholder="Description" rows="2"></textarea></div>
                    <div class="col-md-3"><input class="form-control" name="rating" type="number" step="0.1" placeholder="Rating"></div>
                    <div class="col-md-3"><input class="form-control" name="durationMinutes" type="number" placeholder="Duration"></div>
                    <div class="col-md-3"><input class="form-control" name="price" type="number" step="0.01" placeholder="Price"></div>
                    <div class="col-md-3"><button class="btn btn-danger w-100" type="submit">Add Movie</button></div>
                    <div class="col-md-6"><input class="form-control" name="posterUrl" placeholder="Poster URL"></div>
                    <div class="col-md-6"><input class="form-control" name="bannerUrl" placeholder="Banner URL"></div>
                </div>
            </form>

            <div class="table-responsive admin-table-wrap">
                <table class="table table-dark table-bordered align-middle">
                    <thead><tr><th>Movie</th><th>Details</th><th>Media</th><th>Actions</th></tr></thead>
                    <tbody>
                    <% for (Movie movie : movies) { %>
                        <tr>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/movies/update" method="post" class="admin-inline-form">
                                    <input type="hidden" name="id" value="<%= movie.getId() %>">
                                    <input class="form-control mb-2" name="title" value="<%= movie.getTitle() %>">
                                    <textarea class="form-control" name="description" rows="2"><%= movie.getDescription() %></textarea>
                            </td>
                            <td>
                                    <input class="form-control mb-2" name="genre" value="<%= movie.getGenre() %>">
                                    <input class="form-control mb-2" name="rating" type="number" step="0.1" value="<%= movie.getRating() %>">
                                    <input class="form-control mb-2" name="durationMinutes" type="number" value="<%= movie.getDurationMinutes() %>">
                                    <input class="form-control" name="price" type="number" step="0.01" value="<%= movie.getPrice() %>">
                            </td>
                            <td>
                                    <input class="form-control mb-2" name="posterUrl" value="<%= movie.getPosterUrl() %>">
                                    <input class="form-control" name="bannerUrl" value="<%= movie.getBannerUrl() %>">
                            </td>
                            <td>
                                    <button class="btn btn-danger btn-sm mb-2 w-100" type="submit">Update</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/admin/movies/delete" method="post">
                                    <input type="hidden" name="id" value="<%= movie.getId() %>">
                                    <button class="btn btn-outline-light btn-sm w-100" type="submit">Delete</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </section>
    </div>
</main>
<%@ include file="fragments/footer.jspf" %>
