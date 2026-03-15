package gov.iti.jets.ecommerce.listeners;

import gov.iti.jets.ecommerce.controllers.RegisterController;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

@WebListener
public class AppConfigListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        try (InputStream is = AppConfigListener.class.getClassLoader().getResourceAsStream("info.properties")) {
            properties.load(is);
            BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(properties.getProperty("balance")));
            sce.getServletContext().setAttribute("defaultBalance",balance);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
