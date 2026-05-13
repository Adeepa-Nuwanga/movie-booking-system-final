<%@ page import="com.moviebooking.model.BookingRequest" %>
<%@ page import="com.moviebooking.model.BookingStatus" %>
<%
    BookingRequest bookingRequest = (BookingRequest) request.getAttribute("bookingRequest");
    boolean confirmed = bookingRequest.getStatus() == BookingStatus.CONFIRMED;
%>
<%@ include file="fragments/header.jspf" %>

<main class="container py-5">
    <section class="booking-panel result-panel">
        <span class="status-badge <%= bookingRequest.getStatus().name().toLowerCase() %>">
            <%= bookingRequest.getStatus() %>
        </span>

        <% if (confirmed) { %>
            <h1>Booking confirmed</h1>
            <p>Your request was processed successfully through the FIFO booking queue.</p>
        <% } else { %>
            <h1>Booking rejected</h1>
            <p><%= bookingRequest.getRejectionReason() %></p>
        <% } %>

        <div class="summary-card mt-4">
            <div class="summary-row">
                <span>Request ID</span>
                <strong><%= bookingRequest.getRequestId() %></strong>
            </div>
            <div class="summary-row">
                <span>Showtime</span>
                <strong><%= bookingRequest.getShowtimeId() %></strong>
            </div>
            <div class="summary-row">
                <span>Seats</span>
                <strong><%= String.join(", ", bookingRequest.getSelectedSeats()) %></strong>
            </div>
            <div class="summary-row total">
                <span>Total</span>
                <strong>LKR <%= String.format("%.2f", bookingRequest.getTotalPrice()) %></strong>
            </div>
        </div>

        <div class="action-row">
            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/my-bookings">My Bookings</a>
            <a class="btn btn-danger" href="${pageContext.request.contextPath}/movies">Movies</a>
        </div>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
