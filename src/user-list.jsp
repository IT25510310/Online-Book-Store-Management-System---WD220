<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div style="padding: 2rem 0;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h2 style="font-size: 2.5rem; margin: 0;">Customer Database</h2>
        <div style="display: flex; gap: 10px;">
            <a href="<c:url value='/admin/admins'/>" class="btn btn-outline">Manage Admins</a>
            <a href="<c:url value='/admin/logs'/>" class="btn btn-outline">Activity Logs</a>
        </div>
    </div>

    <!-- Search Bar -->
    <div class="container" style="margin: 0 0 2rem 0; padding: 1.5rem; background: rgba(255,255,255,0.05); border-radius: 8px;">
        <form action="<c:url value='/admin/users'/>" method="get" style="display: flex; gap: 15px; align-items: center;">
            <div style="flex-grow: 1; position: relative;">
                <input type="text" name="query" value="${query}" class="form-control" placeholder="Search by Username, ID, or Full Name..." style="padding-left: 45px;">
                <div style="position: absolute; left: 15px; top: 50%; transform: translateY(-50%); color: #888;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" style="min-width: 120px;">Search</button>
            <c:if test="${not empty query}">
                <a href="<c:url value='/admin/users'/>" class="btn btn-outline" style="min-width: 120px; border-color: #888; color: #888;">Clear</a>
            </c:if>
        </form>
    </div>

    <div class="container" style="margin: 0; width: 100%; padding: 2rem;">
        <div class="cart-table-container">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th style="width: 100px;">User ID</th>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Contact</th>
                        <th>Address</th>
                        <th style="width: 120px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td style="font-weight: bold; color: var(--accent-color);">${user.userId}</td>
                            <td style="font-weight: bold;">${user.username}</td>
                            <td>${user.fullName}</td>
                            <td>${user.email}</td>
                            <td style="font-style: italic; font-size: 0.8rem; color: #888;">${user.details}</td>
                            <td>${user.contactNumber}</td>
                            <td style="font-size: 0.85rem; color: #bbb;">${user.address}</td>
                            <td>
                                <a href="<c:url value='/admin/users/delete/${user.username}'/>" 
                                   class="btn btn-sm" 
                                   style="background-color: transparent; border: 1px solid #c0392b; color: #c0392b;"
                                   onclick="return confirm('Are you sure you want to remove this customer account? All user data will be lost.')">
                                    Remove
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty users}">
                        <tr>
                            <td colspan="6" style="text-align: center; padding: 4rem; color: #888;">
                                <div style="margin-bottom: 1rem;">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="#444" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
                                </div>
                                No customers found matching "${query}"
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
