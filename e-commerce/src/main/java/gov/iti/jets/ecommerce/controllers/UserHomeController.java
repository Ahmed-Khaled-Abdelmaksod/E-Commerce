package gov.iti.jets.ecommerce.controllers;

import gov.iti.jets.ecommerce.beans.dashboard.ProductBean;
import gov.iti.jets.ecommerce.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/home")
public class UserHomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductBean> productBeanList = ProductService.getInstance().getAllFeaturedProducts();
        req.setAttribute("featuredProducts",productBeanList);
        req.getRequestDispatcher("/views/user-home.jsp").forward(req,resp);
    }
}
