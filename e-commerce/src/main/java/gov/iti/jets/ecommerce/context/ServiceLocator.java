package gov.iti.jets.ecommerce.context;

import com.zaxxer.hikari.HikariDataSource;
import gov.iti.jets.ecommerce.config.DataSourceConfig;

// --- DAO Imports ---
import gov.iti.jets.ecommerce.dao.*;
import gov.iti.jets.ecommerce.dao.impl.*;

// --- Service Imports ---
import gov.iti.jets.ecommerce.service.DashboardService;
import gov.iti.jets.ecommerce.service.impl.DashboardServiceImpl;

public class ServiceLocator {

    private static volatile ServiceLocator instance;

    private final DashboardService dashboardService;

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                if (instance == null) {
                    instance = new ServiceLocator();
                }
            }
        }
        return instance;
    }

    private ServiceLocator() {
        // Initialize DataSource
        HikariDataSource dataSource = DataSourceConfig.getDataSource();

        // Initialize DAO layer
        ProductDAO productDAO = new ProductDaoImpl(dataSource);
        OrderDAO orderDAO = new OrderDaoImpl(dataSource);
        OrderItemDAO orderItemDAO = new OrderItemDaoImpl(dataSource);
        UserDAO userDAO = new UserDaoImpl(dataSource);

        // Initialize Service layer with its required dependencies
        this.dashboardService = new DashboardServiceImpl(
            productDAO, 
            orderDAO, 
            orderItemDAO, 
            userDAO
        );
    }

    // --- Getters ---

    public DashboardService getDashboardService() {
        return dashboardService;
    }
}