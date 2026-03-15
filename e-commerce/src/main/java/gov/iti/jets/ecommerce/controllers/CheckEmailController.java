package gov.iti.jets.ecommerce.controllers;

import com.google.gson.Gson;
import gov.iti.jets.ecommerce.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/auth/check-email")
public class CheckEmailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        Map<String,Object> map = new HashMap<>();

        if(AuthService.getInstance().isEmailExist(email)) {
            map.put("available",false);
        }else {
            map.put("available",true);
        }
        Gson gson = new Gson();
        String json = gson.toJson(map);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.println(json);
        writer.flush();
    }
}
