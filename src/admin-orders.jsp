<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<style>
    .status-indicator {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 5px 15px;
        border-radius: 20px;
        background: rgba(255,255,255,0.05);
        border: 1px solid #444;
        width: fit-content;
    }
    .status-dot {
        width: 12px;
        height: 12px;
        border-radius: 50%;
        display: inline-block;
    }
    .dot-green { background-color: #27ae60; box-shadow: 0 0 10px rgba(39, 174, 96, 0.5); }
    .dot-yellow { background-color: #f1c40f; box-shadow: 0 0 10px rgba(241, 196, 15, 0.5); }
    
    .admin-order-card {
        background: var(--card-bg);
        border-radius: 12px;
        padding: 25px;
        margin-bottom: 2rem;
        border: 1px solid #444;
    }

    .modal-overlay {
        display: none;
        position: fixed;
        top: 0; left: 0; width: 100%; height: 100%;
        background: rgba(0,0,0,0.8);
        z-index: 3000;
        align-items: center; justify-content: center;
    }
    .confirm-box {
        background: #222;
        padding: 2.5rem;
        border-radius: 15px;
        border: 1px solid var(--accent-color);
        text-align: center;
        max-width: 450px;
        width: 90%;
    }
    .confirm-btn {
        padding: 10px 25px;
        border-radius: 6px;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.3s;
        border: 2px solid transparent;
        margin: 0 10px;
    }
    .btn-yes:hover { background: #27ae60; color: white; transform: scale(1.1); border-color: #fff; }
    .btn-no:hover { background: #c0392b; color: white; transform: scale(1.1); border-color: #fff; }
</style>

<div style="padding: 2rem 0;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h2 style="font-size: 2.5rem; margin: 0;">Global Order Management</h2>
        <div style="display: flex; gap: 10px;">
            <a href="<c:url value='/admin/users'/>" class="btn btn-outline">Manage Customers</a>
            <a href="<c:url value='/admin/logs'/>" class="btn btn-outline">System Logs</a>
        </div>
    </div>

    <!-- Admin Search Bar -->
    <div class="container" style="margin: 0 0 2.5rem 0; padding: 1.5rem; background: rgba(0,0,0,0.2); border-radius: 8px; border: 1px solid #333;">
        <form action="<c:url value='/admin/orders'/>" method="get" style="display: flex; gap: 15px; align-items: center;">
            <div style="flex-grow: 1; position: relative;">
                <input type="text" name="query" value="${query}" class="form-control" placeholder="Search orders by Username, User ID, or Order ID..." style="padding-left: 45px;">
                <div style="position: absolute; left: 15px; top: 50%; transform: translateY(-50%); color: #888;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" style="min-width: 120px;">Filter Orders</button>
            <c:if test="${not empty query}">
                <a href="<c:url value='/admin/orders'/>" class="btn btn-outline" style="min-width: 100px;">Clear</a>
            </c:if>
        </form>
    </div>

    <c:forEach var="order" items="${orders}">
        <div class="admin-order-card">
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 2rem; margin-bottom: 2rem;">
                <div>
                    <label style="font-size: 0.7rem; color: #666; text-transform: uppercase; display: block; margin-bottom: 5px;">Order ID</label>
                    <span style="font-size: 1.2rem; font-weight: bold; color: var(--accent-color);">${order.orderId}</span>
                </div>
                <div>
                    <label style="font-size: 0.7rem; color: #666; text-transform: uppercase; display: block; margin-bottom: 5px;">Customer (ID)</label>
                    <span style="font-weight: bold;">${order.customerName}</span> <span style="color: #888;">(${order.userId})</span>
                </div>
                <div>
                    <label style="font-size: 0.7rem; color: #666; text-transform: uppercase; display: block; margin-bottom: 5px;">Order Type</label>
                    <span class="btn btn-sm btn-outline" style="cursor: default; border-color: ${order.orderType == 'Express' ? '#e67e22' : '#95a5a6'}; color: ${order.orderType == 'Express' ? '#e67e22' : '#95a5a6'}; padding: 2px 8px; font-size: 0.75rem;">
                        ${order.orderType}
                    </span>
                </div>
                <div>
                    <label style="font-size: 0.7rem; color: #666; text-transform: uppercase; display: block; margin-bottom: 5px;">Current Status</label>
                    <div class="status-indicator">
                        <span class="status-dot ${order.status == 'ERROR' ? 'dot-yellow' : 'dot-green'}"></span>
                        <span style="font-weight: bold; font-size: 0.85rem; color: #eee;">${order.status}</span>
                    </div>
                </div>
                <div style="text-align: right;">
                    <label style="font-size: 0.7rem; color: #666; text-transform: uppercase; display: block; margin-bottom: 5px;">Total Value</label>
                    <span style="font-size: 1.5rem; font-weight: bold; color: #27ae60;">$${order.total}</span>
                </div>
            </div>

            <div style="background: rgba(0,0,0,0.3); padding: 15px; border-radius: 8px; border-left: 3px solid #444; margin-bottom: 1.5rem;">
                <label style="font-size: 0.7rem; color: #666; text-transform: uppercase; display: block; margin-bottom: 5px;">Items Ordered</label>
                <div style="color: #bbb;">${order.itemsSummary}</div>
            </div>

            <div style="display: flex; gap: 10px; justify-content: flex-end; border-top: 1px solid #333; padding-top: 1.5rem;">
                <a href="<c:url value='/admin/orders/edit/${order.orderId}'/>" 
                   class="btn btn-sm" 
                   style="background-color: #2980b9; color: white;">
                    Edit Details
                </a>
                <button class="btn btn-sm btn-outline" onclick="promptStatusChange('${order.orderId}', 'ONGOING')">Mark Ongoing</button>
                <button class="btn btn-sm btn-outline" onclick="promptStatusChange('${order.orderId}', 'DELIVERED')">Mark Delivered</button>
                <form action="<c:url value='/admin/orders/cancel'/>" method="post" style="display: inline;" onsubmit="return confirm('Cancel this order?')">
                    <input type="hidden" name="orderId" value="${order.orderId}">
                    <button type="submit" class="btn btn-sm btn-outline" style="color: #e74c3c; border-color: #e74c3c;">Cancel Order</button>
                </form>
                <button class="btn btn-sm btn-outline" style="color: #f1c40f; border-color: #f1c40f;" onclick="promptStatusChange('${order.orderId}', 'ERROR')">Report Error</button>
            </div>
        </div>
    </c:forEach>
</div>

<!-- Custom Confirmation Modal -->
<div id="statusModal" class="modal-overlay">
    <div class="confirm-box">
        <h3 id="modalMsg" style="margin-bottom: 2rem; font-size: 1.4rem;">Are you sure you want to change status?</h3>
        <form id="statusForm" action="<c:url value='/admin/orders/status'/>" method="post">
            <input type="hidden" id="modalOrderId" name="orderId">
            <input type="hidden" id="modalStatus" name="status">
            <button type="submit" class="confirm-btn btn-yes" style="background: #2ecc71; color: white;">YES</button>
            <button type="button" class="confirm-btn btn-no" style="background: #e74c3c; color: white;" onclick="closeModal()">NO</button>
        </form>
    </div>
</div>

<script>
    function promptStatusChange(orderId, status) {
        document.getElementById('modalOrderId').value = orderId;
        document.getElementById('modalStatus').value = status;
        document.getElementById('modalMsg').innerText = `Change Order ${orderId} to ${status}?`;
        document.getElementById('statusModal').style.display = 'flex';
    }
    function closeModal() {
        document.getElementById('statusModal').style.display = 'none';
    }
</script>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
