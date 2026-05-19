<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div class="container" style="max-width: 500px; margin-top: 4rem; padding: 3rem;">
    <div style="text-align: center; margin-bottom: 2rem;">
        <h2 style="font-size: 2rem;">${role} Login</h2>
        <p style="color: #bbb; margin-top: 0.5rem;">Enter your credentials to continue</p>
    </div>

    <c:if test="${not empty error}">
        <div style="background: rgba(231, 76, 60, 0.2); color: #e74c3c; padding: 1rem; border-radius: 4px; margin-bottom: 2rem; text-align: center; border: 1px solid rgba(231, 76, 60, 0.3);">
            ${error}
        </div>
    </c:if>

    <c:if test="${not empty success}">
        <div style="background: rgba(39, 174, 96, 0.2); color: #27ae60; padding: 1rem; border-radius: 4px; margin-bottom: 2rem; text-align: center; border: 1px solid rgba(39, 174, 96, 0.3);">
            ${success}
        </div>
    </c:if>

    <form action="<c:url value='/authenticate'/>" method="post">
        <input type="hidden" name="role" value="${role}">
        <input type="hidden" name="returnTo" value="${returnTo}">
        
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" class="form-control" required placeholder="Enter username">
            <c:if test="${role == 'ADMIN'}">
                <small style="color: #888;">Hint: Use your administrator credentials</small>
            </c:if>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required placeholder="Enter password">
        </div>

        <div style="margin-top: 2.5rem; text-align: center;">
            <button type="submit" class="btn btn-primary btn-lg" style="min-width: 200px; ${role == 'ADMIN' ? 'background-color: #c0392b;' : ''}">
                Sign In as ${role == 'ADMIN' ? 'Administrator' : 'Customer'}
            </button>
        </div>
    </form>

    <div style="text-align: center; margin-top: 2rem;">
        <c:choose>
            <c:when test="${role == 'USER'}">
                <p style="color: #888; margin-bottom: 1rem;">Don't have an account? <a href="<c:url value='/register${not empty returnTo ? "?returnTo=".concat(returnTo) : ""}'/>" style="color: var(--accent-color); text-decoration: none;">Sign Up here</a></p>
                <a href="<c:url value='/user-auth-choice${not empty returnTo ? "?returnTo=".concat(returnTo) : ""}'/>" style="color: var(--accent-color); text-decoration: none;">&larr; Back to Options</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/login-choice'/>" style="color: var(--accent-color); text-decoration: none;">&larr; Back to Role Selection</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
