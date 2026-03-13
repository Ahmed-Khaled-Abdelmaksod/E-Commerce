package gov.iti.jets.ecommerce.controllers;

import gov.iti.jets.ecommerce.dao.CategorieDAO;
import gov.iti.jets.ecommerce.dao.ProductDAO;
import gov.iti.jets.ecommerce.dao.impl.CategorieDaoImpl;
import gov.iti.jets.ecommerce.dao.impl.ProductDaoImpl;
import gov.iti.jets.ecommerce.entity.Categorie;
import gov.iti.jets.ecommerce.entity.Product;
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
import java.util.List;

@WebServlet(urlPatterns = {"/admin", "/addProduct", "/editProduct", "/deleteProduct"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AdminProductController extends HttpServlet {

    private ProductDAO productDao;
    private CategorieDAO categorieDao;

    @Override
    public void init() throws ServletException {
        productDao = new ProductDaoImpl();
        categorieDao = new CategorieDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDao.findAll();
        List<Categorie> categories = categorieDao.findAll();

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        request.getRequestDispatcher("views/admin-dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/addProduct":
                handleAddProduct(request, response);
                break;
            case "/editProduct":
                handleEditProduct(request, response);
                break;
            case "/deleteProduct":
                handleDeleteProduct(request, response);
                break;
            default:
                response.sendRedirect("admin");
        }
    }

    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setStockQuantity(Integer.parseInt(request.getParameter("quantity")));
        product.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Categorie category = new Categorie();
        category.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        product.setCategory(category);

        product.setImageUrl(processImageUpload(request.getPart("image")));

        productDao.insert(product);
        response.sendRedirect("admin");
    }

    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        Product product = productDao.findById(productId).orElse(new Product());

        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setStockQuantity(Integer.parseInt(request.getParameter("quantity")));

        Categorie category = new Categorie();
        category.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        product.setCategory(category);

        Part imagePart = request.getPart("image");
        if (imagePart != null && imagePart.getSize() > 0) {
            product.setImageUrl(processImageUpload(imagePart));
        }

        productDao.update(product);
        response.sendRedirect("admin");
    }

    // --- AJAX MODIFIED DELETE METHOD ---
    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        boolean isDeleted = productDao.delete(productId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (isDeleted) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Product deleted\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to delete\"}");
        }
    }

    private String processImageUpload(Part imagePart) throws IOException {
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadDirectory = getServletContext().getRealPath("") + File.separator + "static" + File.separator + "images";

            File uploadDir = new File(uploadDirectory);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String savePath = uploadDirectory + File.separator + fileName;
            imagePart.write(savePath);

            return "/static/images/" + fileName;
        }
        return "/static/images/default.jpg";
    }
}