<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div class="details-container">
    <div class="details-left">
        <div style="position: relative;">
            <c:choose>
                <c:when test="${not empty book.imageUrl}">
                    <img src="${book.imageUrl}" alt="${book.title}" class="details-image">
                </c:when>
                <c:otherwise>
                    <div class="no-image-details">No Image Available</div>
                </c:otherwise>
            </c:choose>
            <c:if test="${avgRating > 0}">
                <div style="position: absolute; bottom: 20px; right: 20px; background: rgba(0,0,0,0.8); color: #f1c40f; padding: 5px 15px; border-radius: 20px; font-size: 1.2rem; font-weight: bold; display: flex; align-items: center; gap: 8px; border: 1px solid rgba(241, 196, 15, 0.5); box-shadow: 0 4px 15px rgba(0,0,0,0.5);">
                    <fmt:formatNumber value="${avgRating}" maxFractionDigits="1" />
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="currentColor" stroke="none">
                        <path d="M12 1.7L15 8.3L22.2 9.4L17 14.5L18.2 21.7L12 18.3L5.8 21.7L7 14.5L1.8 9.4L9 8.3L12 1.7Z" />
                    </svg>
                </div>
            </c:if>
        </div>
        
        <!-- Average Rating Section -->
        <div style="margin-top: 2rem; text-align: center; background: white; padding: 1.5rem; border-radius: 8px; border: 1px solid var(--border-color); box-shadow: 0 1px 2px rgba(0,0,0,0.05);">
            <h4 style="margin: 0 0 10px 0; color: var(--text-muted); text-transform: uppercase; font-size: 0.75rem; font-weight: 700; letter-spacing: 0.05em;">Scholarly Rating</h4>
            <div style="font-size: 2.75rem; font-weight: 800; color: var(--accent-color); font-family: 'Playfair Display', serif;">
                <fmt:formatNumber value="${avgRating}" maxFractionDigits="1" minFractionDigits="1" />
                <span style="font-size: 1.1rem; color: var(--text-muted); font-family: 'Inter', sans-serif;">/ 5.0</span>
            </div>
            <div style="display: flex; justify-content: center; gap: 4px; color: var(--accent-color); font-size: 1.25rem; margin-top: 8px;">
                <c:forEach begin="1" end="5" var="i">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="${i <= avgRating ? 'currentColor' : 'none'}" stroke="currentColor" stroke-width="2">
                        <path d="M12 1.7L15 8.3L22.2 9.4L17 14.5L18.2 21.7L12 18.3L5.8 21.7L7 14.5L1.8 9.4L9 8.3L12 1.7Z" />
                    </svg>
                </c:forEach>
            </div>
            <p style="color: var(--text-muted); font-size: 0.85rem; margin-top: 10px; font-weight: 500;">Based on ${fn:length(reviews)} user reviews</p>
        </div>
    </div>
    <div class="details-right">
        <h1 class="details-title" style="font-family: 'Playfair Display', serif; font-size: 3rem; color: var(--primary-color); margin-bottom: 0.5rem; line-height: 1.1;">${book.title}</h1>
        <p class="details-author" style="font-size: 1.25rem; color: var(--text-muted); margin-bottom: 2rem;">by <strong style="color: var(--text-main);">${book.author}</strong></p>
        <div style="margin-bottom: 1.5rem; display: flex; gap: 10px; flex-wrap: wrap;">
            <span class="btn btn-sm btn-outline" style="cursor: default;">${book.language}</span>
            <span class="btn btn-sm btn-primary" style="cursor: default;">${book.genre}</span>
            <span class="btn btn-sm btn-outline" style="cursor: default; border-color: #cbd5e1; color: var(--text-muted);">${book.formatName}</span>
        </div>
        
        <div class="details-price" style="font-size: 2.5rem; color: var(--accent-color); font-weight: 800; margin-bottom: 2rem; font-family: 'Inter', sans-serif;">$${book.price}</div>
        
        <div style="margin-bottom: 2.5rem; padding: 1.5rem; background: var(--secondary-color); border-radius: 4px; border-left: 4px solid var(--primary-color);">
            <h4 style="margin: 0 0 8px 0; color: var(--text-muted); font-size: 0.75rem; text-transform: uppercase; font-weight: 700; letter-spacing: 0.05em;">Catalogue Details</h4>
            <p style="margin: 0; font-weight: 600; color: var(--primary-color); font-size: 1.1rem;">${book.formatDetails}</p>
        </div>

        <div class="details-description" style="margin-bottom: 3rem;">
            <h3 style="font-family: 'Playfair Display', serif; border-bottom: 2px solid var(--secondary-color); padding-bottom: 0.75rem; margin-bottom: 1.25rem; color: var(--text-main);">Abstract & Description</h3>
            <p style="font-size: 1.1rem; line-height: 1.8; color: #444;">${book.description}</p>
        </div>
        
        <div class="details-actions">
            <c:if test="${sessionScope.userRole == 'SUPER_ADMIN' or sessionScope.userRole == 'MODERATOR'}">
                <div style="margin-bottom: 1.5rem; display: flex; flex-direction: column; gap: 8px;">
                    <a href="<c:url value='/books/edit/${book.id}'/>" 
                       class="btn btn-lg" 
                       style="background-color: #2980b9; color: white;">
                        Edit Book Details
                    </a>
                    <c:if test="${sessionScope.userRole == 'SUPER_ADMIN'}">
                        <a href="<c:url value='/books/delete/${book.id}'/>" 
                           class="btn btn-lg" 
                           style="background-color: #c0392b; color: white;" 
                           onclick="return confirm('Are you sure you want to permanently remove this book from the library?')">
                            Delete from Store
                        </a>
                    </c:if>
                </div>
            </c:if>
            <div style="display: flex; gap: 10px; flex-wrap: wrap;">
                <button onclick="addToCartAjax('${book.id}', this)" class="btn btn-lg btn-accent" style="flex: 1; min-width: 150px;">Add to Cart</button>
                <a href="<c:url value='/cart/add/${book.id}?target=cart'/>" class="btn btn-lg btn-primary" style="flex: 1; min-width: 150px;">Buy Now</a>
            </div>
        </div>
        
        <!-- Review Submission Section -->
        <div id="review-section" style="margin-top: 5rem; border-top: 1px solid var(--border-color); padding-top: 4rem;">
            <c:choose>
                <c:when test="${sessionScope.userRole == 'USER'}">
                    <h3 id="review-form-title" style="font-family: 'Playfair Display', serif; margin-bottom: 2rem; color: var(--text-main);">Submit Your Assessment</h3>
                    <div style="background: white; padding: 2.5rem; border-radius: 8px; border: 1px solid var(--border-color); box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                        <form action="<c:url value='/books/review/submit'/>" method="post" id="review-form">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="hidden" name="reviewId" id="reviewId" value="">
                            <div class="form-group" style="margin-bottom: 2rem;">
                                <label style="margin-bottom: 12px; display: block; font-weight: 600; color: var(--text-muted);">Evaluation Rating</label>
                                <div style="display: flex; gap: 20px;">
                                    <c:forEach begin="1" end="5" var="i">
                                        <label style="cursor: pointer; display: flex; align-items: center; gap: 8px;">
                                            <input type="radio" name="rating" id="rating-${i}" value="${i}" ${i == 5 ? 'checked' : ''} style="accent-color: var(--primary-color);">
                                            <span style="color: var(--accent-color); display: flex; align-items: center; gap: 4px; font-weight: 700;">
                                                ${i} 
                                                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="currentColor" stroke="none">
                                                    <path d="M12 1.7L15 8.3L22.2 9.4L17 14.5L18.2 21.7L12 18.3L5.8 21.7L7 14.5L1.8 9.4L9 8.3L12 1.7Z" />
                                                </svg>
                                            </span>
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="comment" style="font-weight: 600; color: var(--text-muted);">Scholarly Feedback</label>
                                <textarea name="comment" id="comment" class="form-control" rows="4" required placeholder="Provide a detailed assessment of this work..."></textarea>
                            </div>
                            <div style="display: flex; gap: 15px; margin-top: 1.5rem;">
                                <button type="submit" id="review-submit-btn" class="btn btn-primary" style="padding: 14px 30px;">Post Assessment</button>
                                <button type="button" id="cancel-edit-btn" class="btn btn-outline" style="display: none;" onclick="cancelEdit()">Cancel Edit</button>
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; padding: 3rem; background: var(--secondary-color); border-radius: 8px; border: 1px solid var(--border-color);">
                        <p style="color: var(--text-muted); font-weight: 500;">Authentication required. Please <a href="<c:url value='/login/user?returnTo=books/${book.id}'/>" style="color: var(--primary-color); font-weight: 700; text-decoration: underline;">Sign In</a> to contribute your assessment.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Reviews List Display -->
        <div style="margin-top: 5rem;">
            <h3 style="font-family: 'Playfair Display', serif; margin-bottom: 2.5rem; display: flex; align-items: center; gap: 15px; color: var(--text-main);">
                Reader Assessments 
                <span style="background: var(--secondary-color); color: var(--primary-color); padding: 4px 12px; border-radius: 4px; font-size: 0.85rem; font-weight: 800; font-family: 'Inter', sans-serif;">${fn:length(reviews)}</span>
            </h3>
            
            <c:if test="${empty reviews}">
                <p style="color: var(--text-muted); font-style: italic;">No documented assessments for this edition yet.</p>
            </c:if>
            
            <c:forEach var="review" items="${reviews}">
                <div style="background: white; padding: 2rem; border-radius: 8px; border: 1px solid var(--border-color); margin-bottom: 2rem; position: relative; border-left: 5px solid var(--primary-color); box-shadow: 0 1px 2px rgba(0,0,0,0.05);">
                    <div style="display: flex; justify-content: space-between; margin-bottom: 1.25rem;">
                        <div style="display: flex; align-items: center; gap: 12px;">
                            <span style="font-weight: 700; color: var(--text-main); font-size: 1.1rem;">
                                ${review.displayReview(sessionScope.userRole == 'SUPER_ADMIN' or sessionScope.userRole == 'MODERATOR')}
                            </span>
                        </div>
                        <div style="display: flex; gap: 2px; color: var(--accent-color);">
                            <c:forEach begin="1" end="5" var="i">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="${i <= review.rating ? 'currentColor' : 'none'}" stroke="currentColor" stroke-width="2">
                                    <path d="M12 1.7L15 8.3L22.2 9.4L17 14.5L18.2 21.7L12 18.3L5.8 21.7L7 14.5L1.8 9.4L9 8.3L12 1.7Z" />
                                </svg>
                            </c:forEach>
                        </div>
                    </div>
                    <p style="color: #444; line-height: 1.8; margin: 0; font-size: 1.05rem;">${review.comment}</p>
                    <div style="margin-top: 20px; display: flex; justify-content: space-between; align-items: center; border-top: 1px solid var(--secondary-color); padding-top: 1.25rem;">
                        <span style="font-size: 0.8rem; color: var(--text-muted); font-weight: 500;">Documented on: ${review.date}</span>
                        <div style="display: flex; gap: 10px;">
                            <c:if test="${sessionScope.userId == review.userId}">
                                <button class="btn btn-sm btn-outline" style="font-size: 0.7rem; padding: 2px 8px; border-color: #2980b9; color: #3498db;" 
                                        onclick="editReview('${review.id}', ${review.rating}, `${fn:escapeXml(review.comment)}`)">Edit</button>
                            </c:if>
                            <c:if test="${sessionScope.userId == review.userId or sessionScope.userRole == 'SUPER_ADMIN'}">
                                <form action="<c:url value='/books/review/delete/${review.id}'/>" method="post" style="display: inline;" onsubmit="return confirm('Delete this review?')">
                                    <input type="hidden" name="bookId" value="${book.id}">
                                    <button type="submit" class="btn btn-sm btn-outline" style="font-size: 0.7rem; padding: 2px 8px; border-color: #c0392b; color: #e74c3c;">Delete</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <script>
            function editReview(id, rating, comment) {
                document.getElementById('reviewId').value = id;
                document.getElementById('comment').value = comment;
                document.getElementById('rating-' + rating).checked = true;
                document.getElementById('review-form-title').innerText = 'Edit Your Review';
                document.getElementById('review-submit-btn').innerText = 'Update Review';
                document.getElementById('cancel-edit-btn').style.display = 'inline-block';
                
                // Scroll to form
                document.getElementById('review-section').scrollIntoView({ behavior: 'smooth' });
            }

            function cancelEdit() {
                document.getElementById('reviewId').value = '';
                document.getElementById('comment').value = '';
                document.getElementById('rating-5').checked = true;
                document.getElementById('review-form-title').innerText = 'Submit Your Review';
                document.getElementById('review-submit-btn').innerText = 'Post Review';
                document.getElementById('cancel-edit-btn').style.display = 'none';
            }
        </script>

        <div style="margin-top: 3rem;">
            <a href="<c:url value='/books'/>" class="btn btn-outline">&larr; Back to Collection</a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
