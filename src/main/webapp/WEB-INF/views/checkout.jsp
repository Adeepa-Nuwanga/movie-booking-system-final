<%@ page import="java.util.List" %>
<%
    List<String> selectedSeats = (List<String>) request.getAttribute("selectedSeats");
    String movieId = (String) request.getAttribute("movieId");
    String showtimeId = (String) request.getAttribute("showtimeId");
    double ticketPrice = (Double) request.getAttribute("ticketPrice");
    double totalPrice = (Double) request.getAttribute("totalPrice");
    String customerName = (String) request.getAttribute("customerName");
    String customerEmail = (String) request.getAttribute("customerEmail");
%>
<%@ include file="fragments/header.jspf" %>

<main class="container py-5">
    <section class="booking-panel checkout-panel">
        <div class="panel-heading">
            <span class="section-kicker">Checkout</span>
            <h1>Confirm your booking</h1>
            <p>Review your selected seats before sending this request to the booking queue.</p>
        </div>

        <div class="row g-4">
            <div class="col-12 col-lg-7">
                <div class="summary-card">
                    <h2>Booking Summary</h2>
                    <div class="summary-row">
                        <span>Movie</span>
                        <strong>Movie <%= movieId %></strong>
                    </div>
                    <div class="summary-row">
                        <span>Showtime</span>
                        <strong><%= showtimeId %></strong>
                    </div>
                    <div class="summary-row">
                        <span>Seats</span>
                        <strong><%= String.join(", ", selectedSeats) %></strong>
                    </div>
                    <div class="summary-row">
                        <span>Ticket Price</span>
                        <strong>LKR <%= String.format("%.2f", ticketPrice) %></strong>
                    </div>
                    <div class="summary-row total">
                        <span>Total</span>
                        <strong>LKR <%= String.format("%.2f", totalPrice) %></strong>
                    </div>
                </div>
            </div>

            <div class="col-12 col-lg-5">
                <form action="${pageContext.request.contextPath}/checkout" method="post" class="summary-card">
                    <h2>Customer Details</h2>
                    <div class="mb-3">
                        <label for="customerName" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="customerName" name="customerName" value="<%= customerName %>" required>
                    </div>
                    <div class="mb-4">
                        <label for="customerEmail" class="form-label">Email</label>
                        <input type="email" class="form-control" id="customerEmail" name="customerEmail" value="<%= customerEmail %>" required>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-danger">Confirm Booking</button>
                        <button type="button" class="btn btn-outline-light" onclick="history.back()">Back</button>
                    </div>
                </form>
            </div>
        </div>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
