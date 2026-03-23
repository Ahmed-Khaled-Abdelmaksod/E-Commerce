package gov.iti.jets.ecommerce.controllers;

import gov.iti.jets.ecommerce.DTO.CartItemDTO;
import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.entity.CartItem;
import gov.iti.jets.ecommerce.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user/cart")
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cartId = (int)req.getSession(false).getAttribute("userCartId");
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        if(cartId != -1 ) {
            cartItemDTOS = CartService.getInstance().getCartItems(cartId);
        }else {
            UserBean user = (UserBean) req.getSession(false).getAttribute("user");
            CartService.getInstance().createCart(user.getUserId());
        }
        req.setAttribute("cartItems",cartItemDTOS);
        req.getRequestDispatcher("/views/cart.jsp").forward(req,resp);
    }
}
