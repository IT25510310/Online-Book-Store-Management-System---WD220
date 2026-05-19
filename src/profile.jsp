<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div class="container" style="max-width: 800px; margin-top: 4rem; padding: 3rem;">
    <div style="display: flex; align-items: center; gap: 2rem; margin-bottom: 3rem; border-bottom: 1px solid #444; padding-bottom: 2rem;">
        <div style="background: var(--accent-color); width: 100px; height: 100px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 3rem; font-weight: bold; color: white; box-shadow: 0 4px 15px rgba(230, 126, 34, 0.3);">
            ${fn:substring(user.fullName, 0, 1)}
        </div>
        <div>
            <h2 style="font-size: 2.5rem; margin: 0;">${user.fullName}</h2>
            <p style="color: var(--accent-color); margin: 5px 0 0 0; font-weight: bold;">User ID: ${user.userId}</p>
        </div>
    </div>

    <c:if test="${not empty success}">
        <div style="background: rgba(39, 174, 96, 0.2); color: #27ae60; padding: 1rem; border-radius: 4px; margin-bottom: 2rem; text-align: center; border: 1px solid rgba(39, 174, 96, 0.3);">
            ${success}
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div style="background: rgba(231, 76, 60, 0.2); color: #e74c3c; padding: 1rem; border-radius: 4px; margin-bottom: 2rem; text-align: center; border: 1px solid rgba(231, 76, 60, 0.3);">
            ${error}
        </div>
    </c:if>

    <form action="<c:url value='/profile/update'/>" method="post">
        <input type="hidden" name="userId" value="${user.userId}">
        <input type="hidden" name="username" value="${user.username}">

        <div style="margin-bottom: 2rem; border-bottom: 1px solid #444; padding-bottom: 1rem;">
            <h3 style="font-size: 1.25rem; color: #888; text-transform: uppercase; letter-spacing: 1px;">General Information</h3>
        </div>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" class="form-control" value="${user.fullName}" required>
            </div>

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" class="form-control" value="${user.email}" required>
            </div>

            <div class="form-group">
                <label for="contactNumber">Contact Number</label>
                <input type="tel" id="contactNumber" name="contactNumber" class="form-control" value="${user.contactNumber}" required>
            </div>

            <div class="form-group">
                <label for="username_display">Username</label>
                <input type="text" id="username_display" class="form-control" value="${user.username}" disabled style="background: rgba(255,255,255,0.05); color: #888;">
                <small style="color: #666;">Username cannot be changed</small>
            </div>
        </div>

        <div class="form-group" style="margin-top: 15px;">
            <label for="address">Home Address</label>
            <textarea id="address" name="address" class="form-control" rows="3" required>${user.address}</textarea>
        </div>

        <div style="margin: 3rem 0 2rem 0; border-bottom: 1px solid #444; padding-bottom: 1rem;">
            <h3 style="font-size: 1.25rem; color: #888; text-transform: uppercase; letter-spacing: 1px;">Security & Password</h3>
            <p style="color: #666; font-size: 0.85rem; margin-top: 0.5rem;">Leave password fields blank if you don't want to change it.</p>
        </div>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
            <div class="form-group">
                <label for="oldPassword">Current Password</label>
                <input type="password" id="oldPassword" name="oldPassword" class="form-control" placeholder="Enter current password">
            </div>

            <div class="form-group">
                <label for="newPassword">New Password</label>
                <input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="Enter new password">
            </div>
        </div>

        <div style="margin-top: 3rem; display: flex; gap: 15px; justify-content: center; flex-wrap: wrap;">
            <button type="submit" class="btn btn-primary btn-lg" style="min-width: 200px;">
                Update Profile
            </button>
            <a href="<c:url value='/books'/>" class="btn btn-outline btn-lg" style="min-width: 200px;">
                Continue Shopping
            </a>
            <a href="<c:url value='/profile/delete'/>" 
               class="btn btn-outline btn-lg" 
               style="min-width: 200px; border-color: #e74c3c; color: #e74c3c;"
               onclick="return confirm('WARNING: Are you sure you want to permanently delete your account? This action cannot be undone.')">
                Delete My Account
            </a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
