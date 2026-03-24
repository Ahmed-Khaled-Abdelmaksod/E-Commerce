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
        this.dashboardService = ServiceLocator.getInstance().getDashboardService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if ("/deleteProduct".equals(action)) {
            handleDeleteProduct(request, response);
        } else {
            handleSaveProduct(request, response);
        }
    }

    private void handleSaveProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            ProductBean bean = new ProductBean();

            String idParam = request.getParameter("productId");
            boolean isEdit = idParam != null && !idParam.isEmpty();

            if (isEdit) {
                bean.setProductId(Integer.parseInt(idParam));
            }

            bean.setName(request.getParameter("name"));
            bean.setDescription(request.getParameter("description"));
            bean.setPrice(new BigDecimal(request.getParameter("price")));
            bean.setStockQuantity(Integer.parseInt(request.getParameter("quantity")));
            bean.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
            bean.setHighlighted(request.getParameter("highlighted") != null);

            // ── Image Handling Logic ───────────────────────────────────────────
            //
            // Priority:
            // 1. If user uploads a new file → use it
            // 2. If user provides a new URL → use it
            // 3. If editing and no new image → keep current image from hidden field
            // 4. If adding new product without image → use default image

            String resolvedImageUrl = null;

            // 1. Check if a file was uploaded
            try {
                Part imagePart = request.getPart("image");
                if (imagePart != null && imagePart.getSize() > 0) {
                    resolvedImageUrl = processImageUpload(imagePart);
                }
            } catch (Exception imgEx) {
                System.err.println("Image upload failed: " + imgEx.getMessage());
            }

            // 2. If no file, check if a URL was provided
            if (resolvedImageUrl == null) {
                String imageUrlParam = request.getParameter("imageUrl");
                if (imageUrlParam != null && !imageUrlParam.trim().isEmpty()) {
                    resolvedImageUrl = imageUrlParam.trim();
                }
            }

            // 3. If editing and still null, keep existing image
            if (resolvedImageUrl == null && isEdit) {
                String currentImageUrl = request.getParameter("currentImageUrl");
                if (currentImageUrl != null && !currentImageUrl.trim().isEmpty()) {
                    resolvedImageUrl = currentImageUrl.trim();
                }
            }

            // 4. Fallback to default image
            if (resolvedImageUrl == null) {
                resolvedImageUrl = "/static/images/default.jpg";
            }

            bean.setImageUrl(resolvedImageUrl);

            dashboardService.saveProduct(bean);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Always redirect back to products tab after saving
        response.sendRedirect(request.getContextPath() + "/admin/dashboard?tab=products");
    }

    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String idStr = request.getParameter("productId");
            if (idStr == null || idStr.isEmpty()) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"No ID provided\"}");
                return;
            }
            int id = Integer.parseInt(idStr);
            boolean deleted = dashboardService.deleteProduct(id);
            response.getWriter().write(deleted ? "{\"status\":\"success\"}" : "{\"status\":\"error\"}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }

    private String processImageUpload(Part part) throws IOException {
        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

        String uploadPath = getServletContext().getRealPath("") + File.separator + "static" + File.separator + "images";
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        part.write(uploadPath + File.separator + uniqueFileName);
        return "/static/images/" + uniqueFileName;
    }
}