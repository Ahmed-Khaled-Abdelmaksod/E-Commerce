package gov.iti.jets.ecommerce.controllers;

import com.google.gson.Gson;
import gov.iti.jets.ecommerce.DTO.CartItemDTO;
import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.enums.CartItemStatus;
import gov.iti.jets.ecommerce.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
            case INSUFFICIENT_CREDIT -> {
                result.put("status", status.toString());
                result.put("message", "Insufficient credit balance for this quantity.");
            }
            default -> {
                result.put("status", "ERROR");
                result.put("message", "Unable to add item to cart.");
            }
        }
        String json = new Gson().toJson(result);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
    private void handleRemovingFromCart(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userCartId") == null) {
            result.put("status", "NOT_LOGGED_IN");
            try {
                resp.getWriter().write(new Gson().toJson(result));
            } catch (IOException ignored) {}
            return;
        }

        int cartId = (int) session.getAttribute("userCartId");

        int cartItemId;
        try {
            cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Invalid cartItemId");
            try {
                resp.getWriter().write(new Gson().toJson(result));
            } catch (IOException ignored) {}
            return;
        }

        List<CartItemDTO> beforeItems = CartService.getInstance().getCartItems(cartId);
        CartItemDTO targetItem = null;
        for (CartItemDTO dto : beforeItems) {
            if (dto.getCartItemId() != null && dto.getCartItemId() == cartItemId) {
                targetItem = dto;
                break;
            }
        }

        if (targetItem == null || targetItem.getProductId() == null) {
            result.put("status", "ERROR");
            result.put("message", "Cart item not found");
            try {
                resp.getWriter().write(new Gson().toJson(result));
            } catch (IOException ignored) {}
            return;
        }

        int productId = targetItem.getProductId();
        boolean removed = CartService.getInstance().removeFromCart(cartId, productId);

        List<CartItemDTO> items = CartService.getInstance().getCartItems(cartId);
        int newCount = items.stream().mapToInt(CartItemDTO::getQuantity).sum();

        result.put("status", removed ? "REMOVED" : "ERROR");
        result.put("cartCount", newCount);
        result.put("removed", removed);

        try {
            resp.getWriter().write(new Gson().toJson(result));
        } catch (IOException ignored) {}
    }
    private void handleUpdatingTheCart(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userCartId") == null) {
            result.put("status", "NOT_LOGGED_IN");
            try {
                resp.getWriter().write(new Gson().toJson(result));
            } catch (IOException ignored) {}
            return;
        }

        int cartId = (int) session.getAttribute("userCartId");
        int cartItemId;
        String direction = req.getParameter("direction");
        try {
            cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Invalid cartItemId");
            try {
                resp.getWriter().write(new Gson().toJson(result));
            } catch (IOException ignored) {}
            return;
        }

        if (direction == null) direction = "";

        List<CartItemDTO> beforeItems = CartService.getInstance().getCartItems(cartId);
        CartItemDTO targetItem = null;
        for (CartItemDTO dto : beforeItems) {
            if (dto.getCartItemId() != null && dto.getCartItemId() == cartItemId) {
                targetItem = dto;
                break;
            }
        }

        if (targetItem == null || targetItem.getProductId() == null) {
            result.put("status", "ERROR");
            result.put("message", "Cart item not found");
            try {
                resp.getWriter().write(new Gson().toJson(result));
            } catch (IOException ignored) {}
            return;
        }

        int productId = targetItem.getProductId();
        int currentQty = targetItem.getQuantity() == null ? 0 : targetItem.getQuantity();

        boolean removed = false;
        String status = "ERROR";
        String message = null;

        if ("plus".equalsIgnoreCase(direction)) {
            CartItemStatus addStatus = CartService.getInstance().addToCart(cartId, productId);
            status = addStatus.toString();
            if (addStatus == CartItemStatus.LOWQUNATITY) {
                message = "product Not available with the needed quantity in the stock!";
            } else if (addStatus == CartItemStatus.INSUFFICIENT_CREDIT) {
                message = "Insufficient credit balance for this quantity.";
            } else if (addStatus == CartItemStatus.NOTFOUND) {
                message = "Product not found";
            } else if (addStatus == CartItemStatus.ERROR) {
                message = "Error while updating cart";
            }
        } else if ("minus".equalsIgnoreCase(direction)) {
            // Decrement by 1. When quantity reaches 0, remove the item.
            if (currentQty <= 1) {
                CartService.getInstance().removeFromCart(cartId, productId);
                removed = true;
                status = "REMOVED";
            } else {
                int newQty = currentQty - 1;
                boolean updated = CartService.getInstance().updateCartItem(cartId, productId, newQty);
                removed = !updated; // if update failed, treat as not removed but it won't change
                status = updated ? "UPDATED" : "ERROR";
                if (!updated) message = "Error while decrementing quantity";
            }
        }

        List<CartItemDTO> items = CartService.getInstance().getCartItems(cartId);
        int newCount = items.stream().mapToInt(CartItemDTO::getQuantity).sum();

        int updatedQty = 0;
        for (CartItemDTO dto : items) {
            if (dto.getCartItemId() != null && dto.getCartItemId() == cartItemId) {
                updatedQty = dto.getQuantity();
                break;
            }
        }

        result.put("status", status);
        result.put("message", message);
        result.put("cartCount", newCount);
        result.put("cartItemId", cartItemId);
        result.put("newQuantity", updatedQty);
        result.put("removed", removed || updatedQty == 0);

        try {
            resp.getWriter().write(new Gson().toJson(result));
        } catch (IOException ignored) {}
    }
}
