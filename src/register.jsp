<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div class="container" style="max-width: 600px; margin-top: 4rem; padding: 3rem;">
    <div style="text-align: center; margin-bottom: 2rem;">
        <h2 style="font-size: 2rem;">Create Your Account</h2>
        <p style="color: #bbb; margin-top: 0.5rem;">Join our community of book lovers</p>
    </div>

    <c:if test="${not empty error}">
        <div style="background: rgba(231, 76, 60, 0.2); color: #e74c3c; padding: 1rem; border-radius: 4px; margin-bottom: 2rem; text-align: center; border: 1px solid rgba(231, 76, 60, 0.3);">
            ${error}
        </div>
    </c:if>

    <form action="<c:url value='/register'/>" method="post">
        <input type="hidden" name="returnTo" value="${returnTo}">
        <div class="form-group">
            <label for="fullName">Full Name</label>
            <input type="text" id="fullName" name="fullName" class="form-control" required placeholder="Enter your full name">
        </div>

        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" class="form-control" required placeholder="Enter your email">
        </div>

        <div class="form-group">
            <label for="contactNumber">Contact Number</label>
            <input type="tel" id="contactNumber" name="contactNumber" class="form-control" required placeholder="Enter your contact number">
        </div>

        <div class="form-group">
            <label for="address">Home Address</label>
            <textarea id="address" name="address" class="form-control" required placeholder="Enter your home address" rows="3"></textarea>
        </div>

        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" class="form-control" required placeholder="Choose a username">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required placeholder="Choose a password">
        </div>

        <div style="margin-top: 2.5rem; text-align: center;">
            <button type="submit" class="btn btn-primary btn-lg" style="min-width: 250px;">
                Create Account
            </button>
        </div>
    </form>

    <div style="text-align: center; margin-top: 2rem;">
        <p style="color: #888;">Already have an account? <a href="<c:url value='/login/user${not empty returnTo ? "?returnTo=".concat(returnTo) : ""}'/>" style="color: var(--accent-color); text-decoration: none;">Sign In here</a></p>
        <a href="<c:url value='/user-auth-choice${not empty returnTo ? "?returnTo=".concat(returnTo) : ""}'/>" style="color: var(--accent-color); text-decoration: none; display: block; margin-top: 1rem;">&larr; Back to Options</a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
