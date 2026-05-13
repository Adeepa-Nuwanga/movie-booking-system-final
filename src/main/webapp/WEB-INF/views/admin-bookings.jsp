<%@ page import="com.moviebooking.model.AdminBookingSummary" %>
<%@ page import="java.util.List" %>
<%
    List<AdminBookingSummary> bookings = (List<AdminBookingSummary>) request.getAttribute("recentBookings");
%>
<%@ include file="fragments/header.jspf" %>
<main class="container py-5">
    <div class="admin-layout">
        <aside class="admin-sidebar">
            <a href="${pageContext.request.contextPath}/admin">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/movies">Movies</a>
            <a href="${pageContext.request.contextPath}/admin/showtimes">Showtimes</a>
            <a class="active" href="${pageContext.request.contextPath}/admin/bookings">Bookings</a>
            <a class="active" href="${pageContext.request.contextPath}/admin/bookings">Queue</a>
        </aside>
        <section class="admin-panel">
            <span class="section-kicker">Booking Monitor</span>
            <h1>Recent Bookings</h1>
            <div class="queue-callout">
                <span>Queue Length</span>
                <strong><%= request.getAttribute("queueLength") %></strong>
                <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/admin/bookings">Queue Monitor</a>
            </div>
            <div class="table-responsive admin-table-wrap">
                <table class="table table-dark table-bordered align-middle">
                    <thead><tr><th>Booking ID</th><th>Customer</th><th>Movie</th><th>Seats</th><th>Status</th><th>Total</th></tr></thead>
                    <tbody>
                    <% for (AdminBookingSummary booking : bookings) { %>
                        <tr>
                            <td><%= booking.getBookingId() %></td>
                            <td><%= booking.getCustomer() %></td>
                            <td><%= booking.getMovie() %></td>
                            <td><%= booking.getSeats() %></td>
                            <td><span class="status-badge"><%= booking.getStatus() %></span></td>
                            <td>LKR <%= String.format("%.2f", booking.getTotal()) %></td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </section>
    </div>
</main>
<%@ include file="fragments/footer.jspf" %>
