<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div style="text-align: center; padding: 4rem 0;">
    <h2 style="font-size: 2.5rem; margin-bottom: 3rem;">Customer Access</h2>
    <p style="color: #bbb; margin-bottom: 3rem; font-size: 1.1rem;">Are you a returning customer or new to our store?</p>
    
    <div style="display: flex; justify-content: center; gap: 40px; flex-wrap: wrap;">
        <!-- Sign In Choice -->
        <a href="<c:url value='/login/user${not empty returnTo ? "?returnTo=".concat(returnTo) : ""}'/>" style="text-decoration: none; color: inherit;">
            <div class="container" style="width: 300px; margin: 0; padding: 3rem; transition: transform 0.3s; cursor: pointer; display: flex; flex-direction: column; align-items: center;" 
                 onmouseover="this.style.transform='translateY(-10px)'" 
                 onmouseout="this.style.transform='translateY(0)'">
                <div style="background: var(--accent-color); width: 60px; height: 60px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin-bottom: 1.5rem;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"></path><polyline points="10 17 15 12 10 7"></polyline><line x1="15" y1="12" x2="3" y2="12"></line></svg>
                </div>
                <h3 style="margin-bottom: 1rem;">Sign In</h3>
                <p style="color: #bbb; font-size: 0.9rem;">Access your account, view orders, and checkout faster.</p>
                <div class="btn btn-primary" style="margin-top: 1.5rem; min-width: 150px;">Login Now</div>
            </div>
        </a>

        <!-- Sign Up Choice -->
        <a href="<c:url value='/register${not empty returnTo ? "?returnTo=".concat(returnTo) : ""}'/>" style="text-decoration: none; color: inherit;">
            <div class="container" style="width: 300px; margin: 0; padding: 3rem; transition: transform 0.3s; cursor: pointer; display: flex; flex-direction: column; align-items: center;"
                 onmouseover="this.style.transform='translateY(-10px)'" 
                 onmouseout="this.style.transform='translateY(0)'">
                <div style="background: #27ae60; width: 60px; height: 60px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin-bottom: 1.5rem;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="8.5" cy="7" r="4"></circle><line x1="20" y1="8" x2="20" y2="14"></line><line x1="23" y1="11" x2="17" y2="11"></line></svg>
                </div>
                <h3 style="margin-bottom: 1rem;">Sign Up</h3>
                <p style="color: #bbb; font-size: 0.9rem;">Create a new account to start shopping today.</p>
                <div class="btn btn-accent" style="margin-top: 1.5rem; min-width: 150px;">Register Now</div>
            </div>
        </a>
    </div>

    <div style="text-align: center; margin-top: 3rem;">
        <a href="<c:url value='/login-choice'/>" style="color: var(--accent-color); text-decoration: none;">&larr; Back to Role Selection</a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
