<%@ page import="com.moviebooking.model.BookingRequest" %>
<%@ page import="java.util.List" %>
<%
    int queueSize = (Integer) request.getAttribute("queueSize");
    List<BookingRequest> pendingRequests = (List<BookingRequest>) request.getAttribute("pendingRequests");
    List<BookingRequest> processedRequests = (List<BookingRequest>) request.getAttribute("processedRequests");
    BookingRequest lastProcessed = (BookingRequest) request.getAttribute("lastProcessed");
%>
<%@ include file="fragments/header.jspf" %>

<main class="container py-5">
    <section class="booking-panel">
        <div class="panel-heading">
            <span class="section-kicker">Admin Monitor</span>
            <h1>Booking Queue</h1>
            <p>Simple FIFO queue monitor for pending and processed booking requests.</p>
        </div>

        <div class="queue-metrics">
            <div class="metric-card">
                <span>Queue Length</span>
                <strong><%= queueSize %></strong>
            </div>
            <div class="metric-card">
                <span>Processed</span>
                <strong><%= processedRequests.size() %></strong>
            </div>
            <div class="metric-card">
                <span>Last Processed</span>
                <strong><%= lastProcessed == null ? "None" : lastProcessed.getRequestId() %></strong>
            </div>
        </div>

        <h2>Pending Requests</h2>
        <div class="table-responsive queue-table-wrap">
            <table class="table table-dark table-bordered align-middle">
                <thead>
                <tr>
                    <th>Request</th>
                    <th>Customer</th>
                    <th>Showtime</th>
                    <th>Seats</th>
                    <th>Total</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <% if (pendingRequests.isEmpty()) { %>
                    <tr><td colspan="6">No pending requests.</td></tr>
                <% } else {
                    for (BookingRequest requestItem : pendingRequests) { %>
                        <tr>
                            <td><%= requestItem.getRequestId() %></td>
                            <td><%= requestItem.getCustomerName() %></td>
                            <td><%= requestItem.getShowtimeId() %></td>
                            <td><%= String.join(", ", requestItem.getSelectedSeats()) %></td>
                            <td>LKR <%= String.format("%.2f", requestItem.getTotalPrice()) %></td>
                            <td><span class="status-badge <%= requestItem.getStatus().name().toLowerCase() %>"><%= requestItem.getStatus() %></span></td>
                        </tr>
                <%  }
                } %>
                </tbody>
            </table>
        </div>

        <h2>Processed Requests</h2>
        <div class="table-responsive queue-table-wrap">
            <table class="table table-dark table-bordered align-middle">
                <thead>
                <tr>
                    <th>Request</th>
                    <th>Customer</th>
                    <th>Showtime</th>
                    <th>Seats</th>
                    <th>Total</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <% if (processedRequests.isEmpty()) { %>
                    <tr><td colspan="6">No processed requests yet.</td></tr>
                <% } else {
                    for (BookingRequest requestItem : processedRequests) { %>
                        <tr>
                            <td><%= requestItem.getRequestId() %></td>
                            <td><%= requestItem.getCustomerName() %></td>
                            <td><%= requestItem.getShowtimeId() %></td>
                            <td><%= String.join(", ", requestItem.getSelectedSeats()) %></td>
                            <td>LKR <%= String.format("%.2f", requestItem.getTotalPrice()) %></td>
                            <td><span class="status-badge <%= requestItem.getStatus().name().toLowerCase() %>"><%= requestItem.getStatus() %></span></td>
                        </tr>
                <%  }
                } %>
                </tbody>
            </table>
        </div>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
