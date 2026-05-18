<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div style="padding: 2rem 0;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h2 style="font-size: 2.5rem; margin: 0;">Activity Logs</h2>
        <c:if test="${sessionScope.userRole == 'SUPER_ADMIN' or sessionScope.userRole == 'MODERATOR'}">
            <a href="<c:url value='/admin/admins'/>" class="btn btn-outline">View All Admins</a>
        </c:if>
    </div>

    <div class="container" style="margin: 0; width: 100%; padding: 2rem;">
        <div class="cart-table-container">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th style="width: 200px;">User</th>
                        <th>Action</th>
                        <th style="width: 200px;">Timestamp</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="log" items="${logs}">
                        <tr>
                            <td style="font-weight: bold; color: var(--accent-color);">${log.username}</td>
                            <td>${log.action}</td>
                            <td style="color: #888; font-family: monospace;">${log.timestamp}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty logs}">
                        <tr>
                            <td colspan="3" style="text-align: center; padding: 3rem; color: #666;">No activity logs found.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
