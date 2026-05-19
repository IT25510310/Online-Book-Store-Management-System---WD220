<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div style="text-align: center; padding: 4rem 0;">
    <h2 style="font-size: 2.5rem; margin-bottom: 3rem;">Welcome! Please Choose Your Role</h2>
    
    <div style="display: flex; justify-content: center; gap: 40px; flex-wrap: wrap;">
        <!-- User Login Choice -->
        <a href="<c:url value='/user-auth-choice'/>" style="text-decoration: none; color: inherit;">
            <div class="container" style="width: 300px; margin: 0; padding: 3rem; transition: transform 0.3s; cursor: pointer; display: flex; flex-direction: column; align-items: center;" 
                 onmouseover="this.style.transform='translateY(-10px)'" 
                 onmouseout="this.style.transform='translateY(0)'">
                <h3 style="margin-bottom: 1rem;">Customer</h3>
                <p style="color: #bbb; font-size: 0.9rem;">Browse our collection and find your next favorite book.</p>
                <div class="btn btn-primary" style="margin-top: 1.5rem; min-width: 150px;">Access as User</div>
            </div>
        </a>

        <!-- Admin Login Choice -->
        <a href="<c:url value='/login/admin'/>" style="text-decoration: none; color: inherit;">
            <div class="container" style="width: 300px; margin: 0; padding: 3rem; transition: transform 0.3s; cursor: pointer; display: flex; flex-direction: column; align-items: center;"
                 onmouseover="this.style.transform='translateY(-10px)'" 
                 onmouseout="this.style.transform='translateY(0)'">
                <h3 style="margin-bottom: 1rem;">Administrator</h3>
                <p style="color: #bbb; font-size: 0.9rem;">Manage inventory, update book details, and remove listings.</p>
                <div class="btn btn-primary" style="margin-top: 1.5rem; min-width: 150px; background-color: #c0392b;">Login as Admin</div>
            </div>
        </a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
