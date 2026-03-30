package gov.iti.jets.ecommerce.controllers;

import gov.iti.jets.ecommerce.DTO.CategoryDTO;
import gov.iti.jets.ecommerce.DTO.ProductDTO;
import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.enums.UserRole;
import gov.iti.jets.ecommerce.service.CategoryService;
import gov.iti.jets.ecommerce.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class GuestProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = ProductService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        List<ProductDTO> products = productService.getAllProducts();
        List<CategoryDTO> categories = categoryService.getAllCatigories();
        req.setAttribute("products", products);
        req.setAttribute("categories", categories);
        UserBean userBean = (UserBean) req.getSession(false).getAttribute("user");
        if(userBean != null && userBean.getRole() == UserRole.ADMIN) {
            req.setAttribute("role","admin");
        }
        req.getRequestDispatcher("/views/guest-sweets.jsp").forward(req, resp);
    }
}
