<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div class="container" style="max-width: 600px; margin-top: 4rem; padding: 3rem;">
    <div style="text-align: center; margin-bottom: 2rem;">
        <h2 style="font-size: 2rem;">${isEdit ? 'Update Administrator Profile' : 'Add New Administrator'}</h2>
        <p style="color: #bbb; margin-top: 0.5rem;">${isEdit ? 'Modify account details and permissions' : 'Create a new account with administrative privileges'}</p>
    </div>

    <form action="<c:url value='/admin/admins/save'/>" method="post">
        <input type="hidden" name="oldUsername" value="${admin.username}">
        
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" class="form-control" value="${admin.username}" required placeholder="Enter username">
            <c:if test="${isEdit}">
                <small style="color: #888;">Changing the username will update all associated records.</small>
            </c:if>
        </div>

        <div class="form-group">
            <label for="fullName">Full Name</label>
            <input type="text" id="fullName" name="fullName" class="form-control" value="${admin.fullName}" required placeholder="Enter full name">
        </div>

        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" class="form-control" value="${admin.email}" required placeholder="Enter email address">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" value="${admin.password}" required placeholder="Enter password">
        </div>

        <div class="form-group">
            <label for="role">Permissions / Role</label>
            <select id="role" name="role" class="form-control" required>
                <option value="SUPER_ADMIN" ${admin.role == 'SUPER_ADMIN' ? 'selected' : ''}>Super Administrator</option>
                <option value="MODERATOR" ${admin.role == 'MODERATOR' ? 'selected' : ''}>Moderator</option>
                <option value="VIEWER" ${admin.role == 'VIEWER' ? 'selected' : ''}>Viewer</option>
            </select>
            <c:if test="${currentRoleLevel < 3}">
                <small style="color: #e74c3c;">Note: You cannot elevate an account above your current level.</small>
            </c:if>
        </div>

        <div style="margin-top: 2.5rem; text-align: center; display: flex; flex-direction: column; align-items: center; gap: 10px;">
            <button type="submit" class="btn btn-primary btn-lg" style="min-width: 250px; background-color: #c0392b;">
                ${isEdit ? 'Save Changes' : 'Create Admin Account'}
            </button>

            <c:if test="${isEdit and currentRoleLevel >= targetRoleLevel}">
                <a href="<c:url value='/admin/admins/delete/${admin.username}'/>" 
                   class="btn btn-outline" 
                   style="min-width: 250px; border-color: #e74c3c; color: #e74c3c;"
                   onclick="return confirm('Are you sure you want to permanently remove this administrator account?${admin.username == sessionScope.username ? " NOTE: This is YOUR account and you will be logged out." : ""}')">
                    Delete ${admin.username == sessionScope.username ? 'My Account' : 'Administrator Account'}
                </a>
            </c:if>
        </div>
    </form>

    <div style="text-align: center; margin-top: 2rem;">
        <a href="<c:url value='/admin/admins'/>" style="color: var(--accent-color); text-decoration: none;">&larr; Back to Admin List</a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
