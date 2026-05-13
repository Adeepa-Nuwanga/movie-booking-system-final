<%@ page import="com.moviebooking.model.Showtime" %>
<%@ page import="java.util.List" %>
<%
    List<Showtime> showtimes = (List<Showtime>) request.getAttribute("showtimes");
%>
<%@ include file="fragments/header.jspf" %>
<main class="container py-5">
    <div class="admin-layout">
        <aside class="admin-sidebar">
            <a href="${pageContext.request.contextPath}/admin">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/movies">Movies</a>
            <a class="active" href="${pageContext.request.contextPath}/admin/showtimes">Showtimes</a>
            <a href="${pageContext.request.contextPath}/admin/bookings">Bookings</a>
            <a href="${pageContext.request.contextPath}/admin/bookings">Queue</a>
        </aside>
        <section class="admin-panel">
            <span class="section-kicker">Schedule Control</span>
            <h1>Showtimes</h1>
            <form class="admin-form" action="${pageContext.request.contextPath}/admin/showtimes" method="post">
                <h2>Add Showtime</h2>
                <div class="row g-3">
                    <div class="col-md-3"><input class="form-control" name="movieId" placeholder="Movie ID" required></div>
                    <div class="col-md-3"><input class="form-control" name="cinemaHall" placeholder="Cinema Hall" required></div>
                    <div class="col-md-3"><input class="form-control" name="date" placeholder="Date" required></div>
                    <div class="col-md-2"><input class="form-control" name="time" placeholder="Time" required></div>
                    <div class="col-md-1"><button class="btn btn-danger w-100" type="submit">Add</button></div>
                </div>
            </form>
            <div class="table-responsive admin-table-wrap">
                <table class="table table-dark table-bordered align-middle">
                    <thead><tr><th>ID</th><th>Movie</th><th>Hall</th><th>Date</th><th>Time</th><th>Actions</th></tr></thead>
                    <tbody>
                    <% for (Showtime showtime : showtimes) { %>
                        <tr>
                            <td><%= showtime.getId() %></td>
                            <td colspan="4">
                                <form action="${pageContext.request.contextPath}/admin/showtimes/update" method="post" class="showtime-edit-form">
                                    <input type="hidden" name="id" value="<%= showtime.getId() %>">
                                    <input class="form-control" name="movieId" value="<%= showtime.getMovieId() %>">
                                    <input class="form-control" name="cinemaHall" value="<%= showtime.getCinemaHall() %>">
                                    <input class="form-control" name="date" value="<%= showtime.getDate() %>">
                                    <input class="form-control" name="time" value="<%= showtime.getTime() %>">
                                    <button class="btn btn-danger btn-sm" type="submit">Update</button>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/showtimes/delete" method="post">
                                    <input type="hidden" name="id" value="<%= showtime.getId() %>">
                                    <button class="btn btn-outline-light btn-sm" type="submit">Delete</button>
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
