<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem; flex-wrap: wrap; gap: 1rem;">
    <h2>
        <c:choose>
            <c:when test="${not empty currentType}">
                ${currentType} Collection
            </c:when>
            <c:when test="${not empty currentLanguage}">
                ${currentLanguage} Books
            </c:when>
            <c:otherwise>
                Our Collection
            </c:otherwise>
        </c:choose>
    </h2>
    <c:if test="${sessionScope.userRole == 'SUPER_ADMIN'}">
        <a href="<c:url value='/books/new'/>" class="btn btn-primary">Add New Book</a>
    </c:if>
</div>

<!-- Search Bar -->
<div class="search-area">
    <form action="<c:url value='/books'/>" method="get" style="display: flex; gap: 15px; align-items: center;">
        <div style="flex-grow: 1; position: relative;">
            <input type="text" name="search" value="${searchQuery}" class="form-control" placeholder="Search the library catalog by title, author, or ISBN..." style="padding-left: 45px;">
            <div style="position: absolute; left: 15px; top: 50%; transform: translateY(-50%); color: #888;">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
            </div>
        </div>
        <button type="submit" class="btn btn-primary" style="min-width: 120px;">Search</button>
        <c:if test="${not empty searchQuery}">
            <a href="<c:url value='/books'/>" class="btn btn-outline" style="min-width: 120px; border-color: #888; color: #888;">Clear</a>
        </c:if>
    </form>
</div>

<div class="book-grid">
    <c:forEach var="book" items="${books}">
        <div style="position: relative;">
            <a href="<c:url value='/books/${book.id}'/>" class="book-card-link">
                <div class="book-card">
                    <div class="card-image">
                        <c:choose>
                            <c:when test="${not empty book.imageUrl}">
                                <img src="${book.imageUrl}" alt="${book.title}">
                            </c:when>
                            <c:otherwise>
                                <div class="no-image-large">No Image</div>
                            </c:otherwise>
                        </c:choose>
                        <span class="card-badge">${book.genre}</span>
                        <c:set var="rating" value="${bookRatings[book.id]}" />
                        <c:if test="${rating > 0}">
                            <div style="position: absolute; bottom: 10px; right: 10px; background: rgba(0,0,0,0.7); color: #f1c40f; padding: 2px 8px; border-radius: 12px; font-size: 0.8rem; font-weight: bold; display: flex; align-items: center; gap: 4px; border: 1px solid rgba(241, 196, 15, 0.3);">
                                <fmt:formatNumber value="${rating}" maxFractionDigits="1" />
                                <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="currentColor" stroke="none">
                                    <path d="M12 1.7L15 8.3L22.2 9.4L17 14.5L18.2 21.7L12 18.3L5.8 21.7L7 14.5L1.8 9.4L9 8.3L12 1.7Z" />
                                </svg>
                            </div>
                        </c:if>
                    </div>
                    <div class="card-content">
                        <h3 class="book-title">${book.title}</h3>
                        <p style="font-size: 0.75rem; color: #888; margin: 4px 0;">${book.displayInfo}</p>
                        <p class="book-price">$${book.price}</p>
                    </div>
                </div>
            </a>
            
            <div style="display: flex; gap: 5px; justify-content: center; margin-top: 10px;">
                <button onclick="addToCartAjax('${book.id}', this)" class="btn btn-sm btn-primary" style="flex: 1;">Add to Cart</button>
            </div>

            <c:if test="${sessionScope.userRole == 'SUPER_ADMIN' or sessionScope.userRole == 'MODERATOR'}">
                <div style="display: flex; gap: 5px; justify-content: center; margin-top: 5px;">
                    <a href="<c:url value='/books/edit/${book.id}'/>" class="btn btn-sm" style="flex: 1; background-color: #2980b9; color: white;">Edit Book Details</a>
                </div>
            </c:if>

            <c:if test="${sessionScope.userRole == 'SUPER_ADMIN'}">
                <div style="display: flex; gap: 5px; justify-content: center; margin-top: 5px;">
                    <a href="<c:url value='/books/delete/${book.id}'/>" class="btn btn-sm" style="flex: 1; background-color: #c0392b; color: white;" onclick="return confirm('Delete this book?')">Delete Book</a>
                </div>
            </c:if>
            </div>
            </c:forEach>
            </div>

<c:if test="${empty books}">
    <div style="text-align: center; padding: 4rem;">
        <p>No books found. Add some to get started!</p>
    </div>
</c:if>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
