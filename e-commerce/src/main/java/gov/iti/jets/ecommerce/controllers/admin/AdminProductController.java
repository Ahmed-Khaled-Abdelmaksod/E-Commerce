package gov.iti.jets.ecommerce.controllers.admin;

import gov.iti.jets.ecommerce.context.ServiceLocator;
import gov.iti.jets.ecommerce.service.DashboardService;
import gov.iti.jets.ecommerce.beans.dashboard.ProductBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/addProduct", "/editProduct", "/deleteProduct"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AdminProductController extends HttpServlet {

    private DashboardService dashboardService;

    @Override
    public void init() throws ServletException {
        // الوصول للخدمة حصرياً عبر ServiceLocator
        this.dashboardService = ServiceLocator.getInstance().getDashboardService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/addProduct":
            case "/editProduct":
                handleSaveProduct(request, response);
                break;
            case "/deleteProduct":
                handleDeleteProduct(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard?tab=products");
        }
    }

    private void handleSaveProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            ProductBean bean = new ProductBean();
            
            // في حالة التعديل سيكون هناك ID، وفي حالة الإضافة سيكون null أو 0
            String idParam = request.getParameter("productId");
            if (idParam != null && !idParam.isEmpty()) {
                bean.setProductId(Integer.parseInt(idParam));
            }

            bean.setName(request.getParameter("name"));
            bean.setDescription(request.getParameter("description"));
            bean.setPrice(new BigDecimal(request.getParameter("price")));
            bean.setStockQuantity(Integer.parseInt(request.getParameter("quantity")));
            bean.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
            
            // التعامل مع الخيار الجديد "Highlight this product"
            bean.setHighlighted(request.getParameter("highlighted") != null);

            // معالجة الصورة
            Part imagePart = request.getPart("image");
            if (imagePart != null && imagePart.getSize() > 0) {
                bean.setImageUrl(processImageUpload(imagePart));
            } else if (bean.getProductId() == null) {
                // صورة افتراضية للمنتجات الجديدة فقط
                bean.setImageUrl("/static/images/default.jpg");
            }

            // تنفيذ الحفظ (إضافة أو تعديل) عبر السيرفس
            dashboardService.saveProduct(bean);

        } catch (Exception e) {
            e.printStackTrace();
            // يمكن إضافة رسالة خطأ هنا في الـ session لتظهر للمستخدم
        }
        response.sendRedirect(request.getContextPath() + "/admin/dashboard?tab=products");
    }

    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try {
            int id = Integer.parseInt(request.getParameter("productId"));
            boolean deleted = dashboardService.deleteProduct(id);
            response.getWriter().write(deleted ? "{\"status\":\"success\"}" : "{\"status\":\"error\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }

    private String processImageUpload(Part part) throws IOException {
        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        // توليد اسم فريد للملف لتجنب التكرار إذا أردت، أو استخدامه كما هو
        String uploadPath = getServletContext().getRealPath("") + File.separator + "static" + File.separator + "images";
        
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();
        
        part.write(uploadPath + File.separator + fileName);
        return "/static/images/" + fileName;
    }
}