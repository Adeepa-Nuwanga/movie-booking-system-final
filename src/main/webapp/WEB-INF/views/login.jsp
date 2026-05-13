<%@ include file="fragments/header.jspf" %>

<main class="auth-page">
    <section class="login-card">
        <div class="login-card__header">
            <span class="login-card__eyebrow">Cinema Access</span>
            <h1>Sign in</h1>
            <p>Use your demo account to continue.</p>
        </div>

        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>

        <% if ("true".equals(request.getParameter("registered"))) { %>
            <div class="alert alert-success" role="alert">
                Account created successfully. Please sign in.
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Username or Email</label>
                <input type="text"
                       class="form-control"
                       id="username"
                       name="username"
                       required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>

            <button type="submit" class="btn btn-danger w-100">Login</button>
        </form>

        <p class="auth-switch">
            New to CineFlex?
            <a href="${pageContext.request.contextPath}/signup">Sign up now</a>
        </p>

        <div class="demo-credentials">
            <strong>Demo credentials</strong>
            <span>admin: admin / admin123</span>
            <span>user: user / user123</span>
        </div>
    </section>
</main>

<%@ include file="fragments/footer.jspf" %>
