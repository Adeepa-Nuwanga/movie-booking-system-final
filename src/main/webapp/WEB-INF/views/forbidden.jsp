<%@ include file="fragments/header.jspf" %>

<main class="container py-5">
    <section class="message-panel">
        <h1>Access denied</h1>
        <p>${errorMessage}</p>
        <a class="btn btn-danger" href="${pageContext.request.contextPath}/movies">Back to movies</a>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
