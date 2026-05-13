<%@ include file="fragments/header.jspf" %>

<main class="auth-page">
    <section class="login-card">
        <div class="login-card__header">
            <span class="login-card__eyebrow">Join CineFlex</span>
            <h1>Create account</h1>
            <p>Sign up to book tickets and manage your cinema visits.</p>
        </div>

        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/signup" method="post">
            <div class="mb-3">
                <label for="name" class="form-label">Full Name</label>
                <input type="text"
                       class="form-control"
                       id="name"
                       name="name"
                       value="<%= request.getAttribute("nameValue") == null ? "" : request.getAttribute("nameValue") %>"
                       required>
            </div>

            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text"
                       class="form-control"
                       id="username"
                       name="username"
                       value="<%= request.getAttribute("usernameValue") == null ? "" : request.getAttribute("usernameValue") %>"
                       required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email"
                       class="form-control"
                       id="email"
                       name="email"
                       value="<%= request.getAttribute("emailValue") == null ? "" : request.getAttribute("emailValue") %>">
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Confirm Password</label>
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
            </div>

            <button type="submit" class="btn btn-danger w-100">Sign Up</button>
        </form>

        <p class="auth-switch">
            Already have an account?
            <a href="${pageContext.request.contextPath}/login">Sign in</a>
        </p>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
