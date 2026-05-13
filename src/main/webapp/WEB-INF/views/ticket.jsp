<%@ page import="com.moviebooking.model.Receipt" %>
<%
    Receipt receipt = (Receipt) request.getAttribute("receipt");
%>
<%@ include file="fragments/header.jspf" %>

<main class="container py-5">
    <section class="ticket-panel ticket-print-area">
        <div class="ticket-header">
            <div>
                <span class="section-kicker">E-Ticket</span>
                <h1>CineFlex</h1>
            </div>
            <span class="status-badge <%= receipt.getBooking().getStatus().name().toLowerCase() %>">
                <%= receipt.getBooking().getStatus() %>
            </span>
        </div>

        <div class="ticket-body">
            <div class="ticket-main">
                <span class="ticket-label">Movie</span>
                <h2><%= receipt.getBooking().getMovieTitle() %></h2>
                <p><%= receipt.getBooking().getShowtimeDate() %> at <%= receipt.getBooking().getShowtimeTime() %></p>
                <p><%= receipt.getBooking().getCinemaHall() %></p>
            </div>

            <div class="ticket-grid">
                <div>
                    <span class="ticket-label">Booking ID</span>
                    <strong><%= receipt.getBooking().getBookingId() %></strong>
                </div>
                <div>
                    <span class="ticket-label">Request ID</span>
                    <strong><%= receipt.getBooking().getRequestId() %></strong>
                </div>
                <div>
                    <span class="ticket-label">Customer</span>
                    <strong><%= receipt.getBooking().getCustomerName() %></strong>
                </div>
                <div>
                    <span class="ticket-label">Email</span>
                    <strong><%= receipt.getBooking().getCustomerEmail() %></strong>
                </div>
                <div>
                    <span class="ticket-label">Seats</span>
                    <strong><%= receipt.getSeatText() %></strong>
                </div>
                <div>
                    <span class="ticket-label">Total</span>
                    <strong><%= receipt.getTotalText() %></strong>
                </div>
                <div>
                    <span class="ticket-label">Confirmed</span>
                    <strong><%= receipt.getConfirmedAtText() %></strong>
                </div>
            </div>
        </div>

        <div class="ticket-actions">
            <button class="btn btn-danger" type="button" onclick="window.print()">Print Ticket</button>
            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/my-bookings">Back to My Bookings</a>
            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/movies">Movies</a>
        </div>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
