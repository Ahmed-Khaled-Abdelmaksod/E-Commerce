package gov.iti.jets.ecommerce.controllers.checkout;

import gov.iti.jets.ecommerce.beans.checkout.CheckoutBean;
import gov.iti.jets.ecommerce.context.ServiceLocator;
import gov.iti.jets.ecommerce.service.CheckoutService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

    private CheckoutService checkoutService;

    @Override
    public void init() throws ServletException {
        // الحصول على الخدمة من الـ ServiceLocator
        this.checkoutService = ServiceLocator.getInstance().getCheckoutService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // // 1. التحقق من وجود مستخدم مسجل الدخول
        // HttpSession session = request.getSession(false);
        // if (session == null || session.getAttribute("userId") == null) {
        //     response.sendRedirect(request.getContextPath() + "/login");
        //     return;
        // }

        // int userId = (Integer) session.getAttribute("userId");

        // 2. جلب بيانات الدفع من الـ Service
        CheckoutBean checkoutBean = checkoutService.getCheckoutDetails(1);

        // 3. تمرير البيانات للـ JSP وعرض الصفحة
        request.setAttribute("checkoutData", checkoutBean);
        request.getRequestDispatcher("/views/checkout/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. التحقق من وجود مستخدم مسجل الدخول
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        // 2. محاولة تنفيذ عملية الدفع
        boolean isSuccess = checkoutService.processCheckout(userId);

        // 3. التوجيه بناءً على النتيجة
        if (isSuccess) {
            // توجيه لصفحة نجاح أو صفحة الطلبات الخاصة بالمستخدم
            response.sendRedirect(request.getContextPath() + "/checkout?status=success");
        } else {
            // العودة لصفحة الدفع مع رسالة خطأ (رصيد غير كافٍ أو منتج نفذ من المخزون)
            response.sendRedirect(request.getContextPath() + "/checkout?error=failed");
        }
    }
}