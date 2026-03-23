package gov.iti.jets.ecommerce.controllers;

import com.google.gson.Gson;
import gov.iti.jets.ecommerce.DTO.CartItemDTO;
import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.entity.CartItem;
import gov.iti.jets.ecommerce.enums.CartItemStatus;
import gov.iti.jets.ecommerce.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/user/cart" , "/user/cart/add" , "/user/cart/remove" , "/user/cart/update"})
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        switch (action) {
            case "/user/cart/add" :
                handleAddingToCart(req, resp);
                break;
            case "/user/cart/remove":
                handleRemovingFromCart(req,resp);
                break;
            case "/user/cart/update" :
                handleUpdatingTheCart(req,resp);
                break;
        }
    }

    private void handleAddingToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int cartId = (int)req.getSession(false).getAttribute("userCartId");
        CartItemStatus status = CartService.getInstance().addToCart(cartId,Integer.parseInt(req.getParameter("productId")));
        Map<String, Object> result = new HashMap<>();
        switch (status) {
            case ADDED -> {
                result.put("status", status.toString());
                result.put("message", "Item added successfully!");
                List<CartItemDTO> items = CartService.getInstance().getCartItems(cartId);
                int newCount = items.stream().mapToInt(CartItemDTO::getQuantity).sum();
                result.put("newCount", newCount);
            }
            case LOWQUNATITY -> {
                result.put("status", status.toString());
                result.put("message", "product Not available with the needed quantity in the stock!");
            }
        }
        String json = new Gson().toJson(result);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
    private void handleRemovingFromCart(HttpServletRequest req, HttpServletResponse resp) {

    }
    private void handleUpdatingTheCart(HttpServletRequest req, HttpServletResponse resp) {

    }
}
