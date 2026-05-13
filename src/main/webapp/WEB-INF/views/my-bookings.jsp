<%@ page import="com.moviebooking.model.Booking" %>
<%@ page import="java.util.List" %>
<%
    List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
    String userKey = (String) request.getAttribute("userKey");
%>
<%@ include file="fragments/header.jspf" %>

<main class="container py-5">
    <section class="ticket-panel">
        <div class="panel-heading">
            <span class="section-kicker">Booking History</span>
            <h1>My Bookings</h1>
            <p>Confirmed e-tickets for <%= userKey %>.</p>
        </div>

        <div class="table-responsive booking-table-wrap">
            <table class="table table-dark table-bordered align-middle">
                <thead>
                <tr>
                    <th>Booking ID</th>
                    <th>Movie</th>
                    <th>Date / Time</th>
                    <th>Seats</th>
                    <th>Status</th>
                    <th>Ticket</th>
                </tr>
                </thead>
                <tbody>
                <% for (Booking booking : bookings) { %>
                    <tr>
                        <td><%= booking.getBookingId() %></td>
                        <td><%= booking.getMovieTitle() %></td>
                        <td><%= booking.getShowtimeDate() %> at <%= booking.getShowtimeTime() %></td>
                        <td><%= String.join(", ", booking.getSeats()) %></td>
                        <td>
                            <span class="status-badge <%= booking.getStatus().name().toLowerCase() %>">
                                <%= booking.getStatus() %>
                            </span>
                        </td>
                        <td>
                            <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/ticket?bookingId=<%= booking.getBookingId() %>">
                                View Ticket
                            </a>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
