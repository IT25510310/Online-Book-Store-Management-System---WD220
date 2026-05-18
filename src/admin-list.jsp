<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div style="padding: 2rem 0;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h2 style="font-size: 2.5rem; margin: 0;">Administrators</h2>
        <div style="display: flex; gap: 10px;">
            <a href="<c:url value='/admin/admins/new'/>" class="btn btn-accent">Add New Admin</a>
            <a href="<c:url value='/admin/logs'/>" class="btn btn-outline">View Activity Logs</a>
        </div>
    </div>

    <c:if test="${param.error == 'last-admin'}">
        <div style="background: rgba(231, 76, 60, 0.2); color: #e74c3c; padding: 1rem; border-radius: 4px; margin-bottom: 2rem; text-align: center; border: 1px solid rgba(231, 76, 60, 0.3);">
            System Error: Cannot remove the last administrator account. There must be at least one admin.
        </div>
    </c:if>

    <c:if test="${param.error == 'permission-denied'}">
        <div style="background: rgba(231, 76, 60, 0.2); color: #e74c3c; padding: 1rem; border-radius: 4px; margin-bottom: 2rem; text-align: center; border: 1px solid rgba(231, 76, 60, 0.3);">
            Permission Denied: You do not have sufficient privileges to manage this administrator account.
        </div>
    </c:if>

    <c:if test="${param.error == 'role-elevation-denied'}">
        <div style="background: rgba(231, 76, 60, 0.2); color: #e74c3c; padding: 1rem; border-radius: 4px; margin-bottom: 2rem; text-align: center; border: 1px solid rgba(231, 76, 60, 0.3);">
            Action Blocked: You cannot assign a role higher than your own.
        </div>
    </c:if>

    <div class="container" style="margin: 0; width: 100%; padding: 2rem;">
        <div class="cart-table-container">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>System Details</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="admin" items="${admins}">
                        <tr>
                            <td style="font-weight: bold; color: var(--accent-color);">${admin.username}</td>
                            <td>${admin.fullName}</td>
                            <td>${admin.email}</td>
                            <td style="font-style: italic; font-size: 0.8rem; color: #888;">${admin.details}</td>
                            <td>
                                <span style="background: ${admin.role == 'SUPER_ADMIN' ? '#c0392b' : 'var(--accent-color)'}; padding: 2px 8px; border-radius: 4px; color: white; font-size: 0.7rem; font-weight: bold;">
                                    ${admin.role}
                                </span>
                            </td>
                            <td>
                                <span style="color: #27ae60; display: flex; align-items: center; gap: 5px;">
                                    <span style="width: 8px; height: 8px; background: #27ae60; border-radius: 50%;"></span>
                                    Active
                                </span>
                            </td>
                            <td>
                                <div style="display: flex; gap: 8px; align-items: center;">
                                    <c:if test="${currentRoleLevel >= 2 and (admin.username == sessionScope.username or currentRoleLevel >= roleLevels[admin.username])}">
                                        <a href="<c:url value='/admin/admins/edit/${admin.username}'/>" 
                                           class="btn btn-sm" 
                                           style="background-color: transparent; border: 1px solid var(--accent-color); color: var(--accent-color);">
                                            Edit
                                        </a>
                                    </c:if>
                                    
                                    <c:if test="${currentRoleLevel >= roleLevels[admin.username]}">
                                        <a href="<c:url value='/admin/admins/delete/${admin.username}'/>" 
                                           class="btn btn-sm" 
                                           style="background-color: transparent; border: 1px solid #c0392b; color: #c0392b;"
                                           onclick="return confirm('Are you sure you want to remove this administrator?${admin.username == sessionScope.username ? " NOTE: This is YOUR account and you will be logged out." : ""}')">
                                            ${admin.username == sessionScope.username ? 'Remove Self' : 'Remove'}
                                        </a>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
