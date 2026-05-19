<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<div class="container" style="max-width: 800px; margin-top: 4rem; padding: 3rem;">
    <div style="text-align: center; margin-bottom: 2rem;">
        <h2 style="font-size: 2rem;">${empty book.id ? 'Add New Book' : 'Edit Book Details'}</h2>
        <p style="color: #bbb;">Configure book information and format-specific attributes</p>
    </div>

    <form action="<c:url value='/books/save'/>" method="post">
        <input type="hidden" name="id" value="${book.id}">
        
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
            <div class="form-group">
                <label for="title">Book Title</label>
                <input type="text" id="title" name="title" value="${book.title}" class="form-control" required placeholder="Enter book title">
            </div>

            <div class="form-group">
                <label for="author">Author</label>
                <input type="text" id="author" name="author" value="${book.author}" class="form-control" required placeholder="Enter author name">
            </div>

            <div class="form-group">
                <label for="language">Language</label>
                <select id="language" name="language" class="form-control" required>
                    <option value="">Select Language</option>
                    <c:forEach var="lang" items="${languages}">
                        <option value="${lang}" ${book.language == lang ? 'selected' : ''}>${lang}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="genre">Genre / Category</label>
                <select id="genre" name="genre" class="form-control" required>
                    <option value="">Select Genre</option>
                    <c:forEach var="t" items="${types}">
                        <option value="${t}" ${book.genre == t ? 'selected' : ''}>${t}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="price">Price ($)</label>
                <input type="number" step="0.01" id="price" name="price" value="${book.price}" class="form-control" required placeholder="0.00">
            </div>

            <div class="form-group">
                <label for="imageUrl">Image URL</label>
                <input type="url" id="imageUrl" name="imageUrl" value="${book.imageUrl}" class="form-control" placeholder="https://example.com/cover.jpg">
            </div>
        </div>

        <div class="form-group" style="margin-top: 15px;">
            <label for="description">Book Description</label>
            <textarea id="description" name="description" class="form-control" rows="3" placeholder="Enter a brief summary...">${book.description}</textarea>
        </div>

        <!-- Format Selection -->
        <div style="margin: 3rem 0 1.5rem 0; border-bottom: 1px solid #444; padding-bottom: 1rem;">
            <h3 style="font-size: 1.25rem; color: #888; text-transform: uppercase; letter-spacing: 1px;">Book Format & Inventory</h3>
        </div>

        <div class="form-group">
            <label for="format">Product Format</label>
            <select id="format" name="format" class="form-control" onchange="toggleFormatFields()" required>
                <option value="PRINTED" ${format == 'PRINTED' ? 'selected' : ''}>Physical Printed Book</option>
                <option value="EBOOK" ${format == 'EBOOK' ? 'selected' : ''}>Digital E-Book</option>
            </select>
        </div>

        <!-- Printed Book Specific Fields -->
        <div id="printed-fields" style="display: ${format == 'EBOOK' ? 'none' : 'grid'}; grid-template-columns: 1fr 1fr; gap: 20px;">
            <div class="form-group">
                <label for="weightKG">Weight (KG)</label>
                <input type="number" step="0.1" id="weightKG" name="weightKG" value="${book.weightKG}" class="form-control" placeholder="0.5">
            </div>
            <div class="form-group">
                <label for="stockQuantity">Stock Quantity</label>
                <input type="number" id="stockQuantity" name="stockQuantity" value="${book.stockQuantity}" class="form-control" placeholder="0">
            </div>
        </div>

        <!-- E-Book Specific Fields -->
        <div id="ebook-fields" style="display: ${format == 'EBOOK' ? 'grid' : 'none'}; grid-template-columns: 1fr 1fr; gap: 20px;">
            <div class="form-group">
                <label for="fileSizeMB">File Size (MB)</label>
                <input type="number" step="0.1" id="fileSizeMB" name="fileSizeMB" value="${book.fileSizeMB}" class="form-control" placeholder="5.0">
            </div>
            <div class="form-group">
                <label for="downloadUrl">Download URL</label>
                <input type="text" id="downloadUrl" name="downloadUrl" value="${book.downloadUrl}" class="form-control" placeholder="/downloads/book.pdf">
            </div>
        </div>

        <div style="margin-top: 3rem; display: flex; flex-direction: column; gap: 10px; align-items: center;">
            <div style="display: flex; gap: 15px;">
                <button type="submit" class="btn btn-primary btn-lg" style="min-width: 250px;">
                    ${empty book.id ? 'Create Book Entry' : 'Update Book Details'}
                </button>
                <a href="<c:url value='/admin/books'/>" class="btn" style="background: #444; color: white; padding: 12px 30px;">Cancel</a>
            </div>
            
            <c:if test="${not empty book.id and sessionScope.userRole == 'SUPER_ADMIN'}">
                <div style="margin-top: 2rem; width: 100%; border-top: 1px solid #444; padding-top: 2rem; text-align: center;">
                    <a href="<c:url value='/books/delete/${book.id}'/>" 
                       class="btn" 
                       style="background-color: #c0392b; color: white; min-width: 250px;"
                       onclick="return confirm('WARNING: Are you sure you want to permanently delete this book?')">
                        Delete Book from Store
                    </a>
                </div>
            </c:if>
        </div>
    </form>
</div>

<script>
    function toggleFormatFields() {
        const format = document.getElementById('format').value;
        const printed = document.getElementById('printed-fields');
        const ebook = document.getElementById('ebook-fields');
        
        if (format === 'EBOOK') {
            printed.style.display = 'none';
            ebook.style.display = 'grid';
        } else {
            printed.style.display = 'grid';
            ebook.style.display = 'none';
        }
    }
</script>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
