document.addEventListener("DOMContentLoaded", function() {
    loadTab('products');
});

function loadTab(tabName) {
    const tabs = document.querySelectorAll('.custom-tab');
    tabs.forEach(tab => tab.classList.remove('active'));
    document.getElementById('tab-' + tabName).classList.add('active');

    const contentDiv = document.getElementById('dashboard-content');
    contentDiv.innerHTML = '<div class="text-center mt-5 text-muted-custom"><div class="spinner-border spinner-border-sm mr-2"></div> Loading...</div>';

    var xhr = new XMLHttpRequest();
    
    // 🟢 استخدام مسار الـ Context المكتوب في الـ pom.xml (/ecommerce)
    xhr.open('GET', '/ecommerce/admin/dashboard?tab=' + tabName, true); 
    
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                contentDiv.innerHTML = xhr.responseText;
            } else {
                console.error("AJAX Error: " + xhr.status);
                contentDiv.innerHTML = '<div class="alert alert-danger mt-4">Error loading data. Status: ' + xhr.status + '</div>';
            }
        }
    };
    xhr.send();
}