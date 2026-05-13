<%@ page import="com.moviebooking.model.Seat" %>
<%@ page import="com.moviebooking.model.SeatStatus" %>
<%@ page import="com.moviebooking.model.Showtime" %>
<%@ page import="java.util.List" %>
<%
    Showtime showtime = (Showtime) request.getAttribute("showtime");
    List<Seat> seats = showtime.getSeatMap().getAllSeats();
    double ticketPrice = (Double) request.getAttribute("ticketPrice");
    String currentRow = "";
%>
<%@ include file="fragments/header.jspf" %>

<main>
    <section class="container py-5">
        <div class="booking-steps">
            <span>Pick a Movie</span>
            <span>Your Details</span>
            <span class="active">Pick a Seat</span>
            <span>Summary</span>
        </div>

        <div class="seat-shell">
            <div class="seat-main">
                <span class="section-kicker"><%= showtime.getCinemaHall() %> | <%= showtime.getDate() %> | <%= showtime.getTime() %></span>
                <h1>Select your seats</h1>

                <% if (request.getAttribute("errorMessage") != null) { %>
                    <div class="alert alert-danger" role="alert">
                        <%= request.getAttribute("errorMessage") %>
                    </div>
                <% } %>

                <div class="screen">SCREEN</div>

                <form action="${pageContext.request.contextPath}/seats" method="post" id="seatForm">
                    <input type="hidden" name="showtimeId" value="<%= showtime.getId() %>">
                    <div class="seat-grid">
                        <% for (Seat seat : seats) {
                            if (!currentRow.equals(seat.getRow())) {
                                if (!currentRow.isEmpty()) { %>
                                    </div>
                                <% }
                                currentRow = seat.getRow(); %>
                                <div class="seat-row">
                                    <span class="seat-row__label"><%= currentRow %></span>
                            <% }
                            boolean disabled = seat.getStatus() == SeatStatus.BOOKED || seat.getStatus() == SeatStatus.HELD;
                        %>
                            <button type="button"
                                    class="seat-button <%= seat.getStatus().name().toLowerCase() %>"
                                    data-seat="<%= seat.getSeatCode() %>"
                                    <%= disabled ? "disabled" : "" %>>
                                <%= seat.getNumber() %>
                            </button>
                        <% } %>
                        <% if (!currentRow.isEmpty()) { %>
                            </div>
                        <% } %>
                    </div>

                    <div id="selectedSeatInputs"></div>

                    <div class="seat-actions">
                        <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/showtimes?movieId=<%= showtime.getMovieId() %>">Back to Showtimes</a>
                        <button type="submit" class="btn btn-danger">Proceed to Checkout</button>
                    </div>
                </form>
            </div>

            <aside class="seat-summary">
                <span class="section-kicker">Summary</span>
                <h2>Your selection</h2>
                <div class="legend">
                    <span><i class="legend-box available"></i> Available</span>
                    <span><i class="legend-box selected"></i> Selected</span>
                    <span><i class="legend-box booked"></i> Booked / Held</span>
                </div>
                <div class="summary-line">
                    <span>Seats</span>
                    <strong id="seatCount">0</strong>
                </div>
                <div class="summary-line">
                    <span>Selected</span>
                    <strong id="selectedSeatText">None</strong>
                </div>
                <div class="summary-line total">
                    <span>Total</span>
                    <strong>LKR <span id="totalPrice">0.00</span></strong>
                </div>
            </aside>
        </div>
    </section>
</main>

<script>
    const ticketPrice = <%= ticketPrice %>;
    const selectedSeats = [];
    const selectedSeatInputs = document.getElementById("selectedSeatInputs");
    const seatCount = document.getElementById("seatCount");
    const selectedSeatText = document.getElementById("selectedSeatText");
    const totalPrice = document.getElementById("totalPrice");

    document.querySelectorAll(".seat-button.available").forEach((button) => {
        button.addEventListener("click", () => {
            const seatCode = button.dataset.seat;
            const index = selectedSeats.indexOf(seatCode);

            if (index >= 0) {
                selectedSeats.splice(index, 1);
                button.classList.remove("selected");
            } else {
                selectedSeats.push(seatCode);
                button.classList.add("selected");
            }

            renderSeatSummary();
        });
    });

    function renderSeatSummary() {
        selectedSeatInputs.innerHTML = "";
        selectedSeats.forEach((seatCode) => {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "selectedSeats";
            input.value = seatCode;
            selectedSeatInputs.appendChild(input);
        });

        seatCount.textContent = selectedSeats.length;
        selectedSeatText.textContent = selectedSeats.length === 0 ? "None" : selectedSeats.join(", ");
        totalPrice.textContent = (selectedSeats.length * ticketPrice).toFixed(2);
    }
</script>

<%@ include file="fragments/footer.jspf" %>
