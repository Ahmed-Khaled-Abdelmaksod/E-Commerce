package gov.iti.jets.ecommerce.controllers.admin;

import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.context.ServiceLocator;
import gov.iti.jets.ecommerce.dao.ProductDAO;
import gov.iti.jets.ecommerce.entity.Category;
import gov.iti.jets.ecommerce.entity.Product;
import jakarta.persistence.EntityManager;
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
import java.sql.Timestamp;

@WebServlet(urlPatterns = {"/addProduct", "/editProduct", "/deleteProduct", "/addCategory"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AdminProductController extends HttpServlet {

    private ProductDAO productDao;

    @Override
    public void init() throws ServletException {
        this.productDao = ServiceLocator.getInstance().getProductDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/addProduct": handleAddProduct(request, response); break;
            case "/editProduct": handleEditProduct(request, response); break;
            case "/deleteProduct": handleDeleteProduct(request, response); break;
            case "/addCategory": handleAddCategory(request, response); break;
            default: response.sendRedirect(request.getContextPath() + "/admin/dashboard?tab=products");
        }
    }

    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin(); // CRITICAL: Start the transaction

            Product product = new Product();
            product.setName(request.getParameter("name"));
            product.setDescription(request.getParameter("description"));
            product.setPrice(new BigDecimal(request.getParameter("price")));
            product.setStockQuantity(Integer.parseInt(request.getParameter("quantity")));
            product.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            Category category = em.find(Category.class, Integer.parseInt(request.getParameter("categoryId")));
            product.setCategory(category);
            product.setImageUrl(processImageUpload(request.getPart("image")));

            productDao.insert(em, product);

            em.getTransaction().commit(); // CRITICAL: Save to MySQL
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        response.sendRedirect(request.getContextPath() + "/admin/dashboard?tab=products");
    }

    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = productDao.findById(em, productId).orElseThrow();

            product.setName(request.getParameter("name"));
            product.setDescription(request.getParameter("description"));
            product.setPrice(new BigDecimal(request.getParameter("price")));
            product.setStockQuantity(Integer.parseInt(request.getParameter("quantity")));

            Category category = em.find(Category.class, Integer.parseInt(request.getParameter("categoryId")));
            product.setCategory(category);

            Part imagePart = request.getPart("image");
            if (imagePart != null && imagePart.getSize() > 0) {
                product.setImageUrl(processImageUpload(imagePart));
            }

            productDao.update(em, product);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        response.sendRedirect(request.getContextPath() + "/admin/dashboard?tab=products");
    }

    private void handleAddCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("categoryName");
        String desc = request.getParameter("categoryDesc");
        response.setContentType("application/json");

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Category cat = new Category();
            cat.setName(name);
            cat.setDescription(desc);
            em.persist(cat);
            em.getTransaction().commit();
            response.getWriter().write("{\"status\":\"success\", \"id\":" + cat.getCategoryId() + ", \"name\":\"" + cat.getName() + "\"}");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            response.setStatus(500);
        } finally { em.close(); }
    }

    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("productId"));
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            boolean deleted = productDao.delete(em, id);
            em.getTransaction().commit();
            response.setContentType("application/json");
            response.getWriter().write(deleted ? "{\"status\":\"success\"}" : "{\"status\":\"error\"}");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            response.setStatus(500);
        } finally { em.close(); }
    }

    private String processImageUpload(Part part) throws IOException {
        if (part != null && part.getSize() > 0) {
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            String path = getServletContext().getRealPath("") + File.separator + "static" + File.separator + "images";
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();
            part.write(path + File.separator + fileName);
            return "/static/images/" + fileName;
        }
        return "/static/images/default.jpg";
    }
}