<%@ include file="fragments/header.jspf" %>
<main class="container py-5">
    <div class="admin-layout">
        <aside class="admin-sidebar">
            <a class="active" href="${pageContext.request.contextPath}/admin">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/movies">Movies</a>
            <a href="${pageContext.request.contextPath}/admin/showtimes">Showtimes</a>
            <a href="${pageContext.request.contextPath}/admin/bookings">Bookings</a>
            <a href="${pageContext.request.contextPath}/admin/bookings">Queue</a>
        </aside>
        <section class="admin-panel">
            <span class="section-kicker">Admin Panel</span>
            <h1>Dashboard</h1>
            <div class="stat-grid">
                <div class="stat-card"><span>Total Movies</span><strong><%= request.getAttribute("totalMovies") %></strong></div>
                <div class="stat-card"><span>Total Showtimes</span><strong><%= request.getAttribute("totalShowtimes") %></strong></div>
                <div class="stat-card"><span>Queue Length</span><strong><%= request.getAttribute("queueLength") %></strong></div>
                <div class="stat-card"><span>Recent Bookings</span><strong><%= request.getAttribute("recentBookingsCount") %></strong></div>
            </div>
        </section>
    </div>
</main>
<%@ include file="fragments/footer.jspf" %>
