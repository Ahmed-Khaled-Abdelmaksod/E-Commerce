package gov.iti.jets.ecommerce.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import gov.iti.jets.ecommerce.enums.CartItemStatus;
import gov.iti.jets.ecommerce.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart/merge")
public class CartMergeController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userCartId") == null) {
            result.put("status", "NOT_LOGGED_IN");
            resp.getWriter().write(new Gson().toJson(result));
            return;
        }

        int cartId = (int) session.getAttribute("userCartId");

        // Read JSON body: [{ "productId": 1, "quantity": 2 }, ...]
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String body = sb.toString().trim();
        if (body.isEmpty() || body.equals("[]")) {
            result.put("status", "EMPTY");
            result.put("message", "No items to merge");
            resp.getWriter().write(new Gson().toJson(result));
            return;
        }

        JsonArray items = JsonParser.parseString(body).getAsJsonArray();
        int merged = 0;
        int failed = 0;

        for (JsonElement elem : items) {
            int productId = elem.getAsJsonObject().get("productId").getAsInt();
            int quantity = elem.getAsJsonObject().get("quantity").getAsInt();

            for (int i = 0; i < quantity; i++) {
                CartItemStatus status = CartService.getInstance().addToCart(cartId, productId);
                if (status == CartItemStatus.ADDED) {
                    merged++;
                } else {
                    failed++;
                    break; // stop adding this product if it fails (stock/credit issue)
                }
            }
        }

        // Refresh session cart count
        int newCount = CartService.getInstance().getCartItems(cartId)
                .stream()
                .mapToInt(item -> item.getQuantity() == null ? 0 : item.getQuantity())
                .sum();
        session.setAttribute("cartCount", newCount);

        result.put("status", "OK");
        result.put("merged", merged);
        result.put("failed", failed);
        result.put("cartCount", newCount);

        resp.getWriter().write(new Gson().toJson(result));
    }
}
