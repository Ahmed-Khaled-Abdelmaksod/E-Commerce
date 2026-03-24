<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Sweet Delights</title>
    
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=DM+Serif+Display:ital@0;1&display=swap" rel="stylesheet">

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
    
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/globals.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/static/css/admin/dashboard.css" rel="stylesheet">
</head>
<body class="bg-white">

    <!--Include Header-->
    <jsp:include page="/static/html/admin-header.html" />

    <!-- Header Section (Beige Background) -->
    <div class="dash-header-bg py-5">
        <div class="container">
            <h1 class="font-serif fw-bold text-dark mb-1" style="font-size: 2.5rem; letter-spacing: -1px;">Admin Dashboard</h1>
            <p class="text-muted-custom mb-0" style="font-size: 0.95rem;">Manage your sweet shop</p>
        </div>
    </div>

    <!-- Main Content Area -->
    <div class="container mt-5">
        
        <!-- Tabs (Exact match styling) -->
        <div class="custom-tabs-container mb-5">
            <div id="tab-products" class="custom-tab active" onclick="loadTab('products')">
                <i class="bi bi-box-seam"></i> Products
            </div>
            <div id="tab-customers" class="custom-tab" onclick="loadTab('customers')">
                <i class="bi bi-people"></i> Customers
            </div>
            <div id="tab-orders" class="custom-tab" onclick="loadTab('orders')">
                <i class="bi bi-receipt"></i> Orders
            </div>
        </div>

        <!-- Dynamic Content from AJAX -->
        <div id="dashboard-content">
            <!-- Data will load here -->
        </div>

    </div>

    <!-- AJAX JS -->
    <script src="${pageContext.request.contextPath}/static/js/admin/dashboard.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function ajaxAddCategory() {
            const name = document.getElementById('newCategoryName').value;
            const desc = document.getElementById('newCategoryDesc').value;
            const ctx = "${pageContext.request.contextPath}";

            if(!name.trim()) { alert("Name required"); return; }

            fetch(ctx + '/addCategory', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'categoryName=' + encodeURIComponent(name) + '&categoryDesc=' + encodeURIComponent(desc)
            })
                .then(res => res.json())
                .then(data => {
                    if(data.status === 'success') {
                        // Update ALL dropdowns in the UI
                        document.querySelectorAll('.category-dropdown').forEach(dropdown => {
                            const option = new Option(data.name, data.id);
                            dropdown.add(option);
                        });

                        // Hide Category Modal
                        const catModalEl = document.getElementById('addCategoryModal');
                        bootstrap.Modal.getOrCreateInstance(catModalEl).hide();

                        // Reset inputs
                        document.getElementById('newCategoryName').value = '';
                        document.getElementById('newCategoryDesc').value = '';
                    }
                })
                .catch(err => console.error(err));
        }
    </script>
</body>
</html>