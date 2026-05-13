<%@ include file="fragments/header.jspf" %>
<main class="container py-5">
    <section class="admin-panel forbidden-panel">
        <span class="section-kicker">Unauthorized</span>
        <h1>Admin access required</h1>
        <p>This page is available only for ADMIN users. Connect your login component by setting session role to ADMIN.</p>
        <div class="d-flex gap-2 flex-wrap">
            <a class="btn btn-danger" href="${pageContext.request.contextPath}/movies">Movies</a>
            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/admin">Admin</a>
        </div>
    </section>
</main>
<%@ include file="fragments/footer.jspf" %>
