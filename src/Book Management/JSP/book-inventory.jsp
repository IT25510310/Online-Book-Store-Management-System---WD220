<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div style="padding: 2rem 0;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h2 style="font-size: 2.5rem; margin: 0;">Book Inventory</h2>
        <div style="display: flex; gap: 10px;">
            <a href="<c:url value='/books/new'/>" class="btn btn-accent">Add New Book</a>
            <a href="<c:url value='/admin/users'/>" class="btn btn-outline">Manage Customers</a>
        </div>
    </div>

    <div class="container" style="margin: 0; width: 100%; padding: 2rem;">
        <div class="cart-table-container">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th style="width: 80px;">Cover</th>
                        <th>Title / Format</th>
                        <th>Author</th>
                        <th>Category</th>
                        <th>Inventory / Size</th>
                        <th>Price</th>
                        <th style="width: 150px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty book.imageUrl}">
                                        <img src="${book.imageUrl}" alt="${book.title}" style="width: 50px; height: 70px; object-fit: cover; border-radius: 4px;">
                                    </c:when>
                                    <c:otherwise>
                                        <div style="width: 50px; height: 70px; background: #333; display: flex; align-items: center; justify-content: center; font-size: 0.6rem; color: #666; border-radius: 4px;">No Image</div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div style="font-weight: bold;">${book.title}</div>
                                <div style="font-size: 0.75rem; color: #888; margin-top: 4px;">
                                    <span style="background: rgba(255,255,255,0.1); padding: 1px 6px; border-radius: 10px;">${book.formatName}</span>
                                </div>
                            </td>
                            <td>${book.author}</td>
                            <td>
                                <span style="background: rgba(230, 126, 34, 0.1); color: var(--accent-color); padding: 2px 8px; border-radius: 4px; font-size: 0.8rem; font-weight: bold;">
                                    ${book.genre}
                                </span>
                            </td>
                            <td style="font-size: 0.85rem; color: #bbb;">
                                ${book.formatDetails}
                            </td>
                            <td style="font-weight: bold; color: #27ae60;">$${book.price}</td>
                            <td>
                                <div style="display: flex; flex-direction: column; gap: 5px;">
                                    <a href="<c:url value='/books/edit/${book.id}'/>" 
                                       class="btn btn-sm" 
                                       style="background-color: #2980b9; color: white;">
                                        Edit Details
                                    </a>
                                    <c:if test="${sessionScope.userRole == 'SUPER_ADMIN'}">
                                        <a href="<c:url value='/books/delete/${book.id}'/>" 
                                           class="btn btn-sm" 
                                           style="background-color: #c0392b; color: white;"
                                           onclick="return confirm('Are you sure you want to permanently delete this book?')">
                                            Delete Book
                                        </a>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty books}">
                        <tr>
                            <td colspan="7" style="text-align: center; padding: 4rem; color: #888;">No books found in the inventory.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
