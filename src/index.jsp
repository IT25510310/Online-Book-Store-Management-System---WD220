<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div style="text-align: center; padding: 5rem 0; color: #fff;">
    <h2 style="font-size: 3.5rem; margin-bottom: 1rem; text-shadow: 2px 2px 8px rgba(0,0,0,0.6);">Welcome to our Book Store</h2>
    <p style="font-size: 1.25rem; max-width: 600px; margin: 0 auto 2.5rem; color: #bbb;">Explore our curated collection of literary treasures, from timeless classics to modern bestsellers.</p>
    <div>
        <a href="<c:url value='/books'/>" class="btn btn-primary btn-lg" style="box-shadow: 0 4px 15px rgba(230, 126, 34, 0.4);">Browse the Collection</a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
