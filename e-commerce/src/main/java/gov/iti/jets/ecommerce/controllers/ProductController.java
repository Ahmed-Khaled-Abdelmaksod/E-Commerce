package gov.iti.jets.ecommerce.controllers;

import gov.iti.jets.ecommerce.DTO.CategoryDTO;
import gov.iti.jets.ecommerce.DTO.ProductDTO;
import gov.iti.jets.ecommerce.service.CategoryService;
import gov.iti.jets.ecommerce.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/products")
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = ProductService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        List<ProductDTO> products  =  productService.getAllProducts();
        List<CategoryDTO> categories = categoryService.getAllCatigories();
        req.setAttribute("products",products);
        req.setAttribute("categories",categories);
        req.getRequestDispatcher("/views/sweets.jsp").forward(req,resp);
    }

}
