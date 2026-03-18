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
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

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
        HikariDataSource dataSource = DataSourceConfig.getDataSource();

        this.productDAO = new ProductDaoImpl();
        this.categoryDAO = new CategoryDaoImpl();

        OrderDAO orderDAO = new OrderDaoImpl(dataSource);
        OrderItemDAO orderItemDAO = new OrderItemDaoImpl(dataSource);
        UserDAO userDAO = UserDaoImpl.getInstance();

        this.dashboardService = new DashboardServiceImpl(
                productDAO,
                orderDAO,
                orderItemDAO,
                userDAO,
                categoryDAO
        );
    }

    // --- Getters ---
    public DashboardService getDashboardService() {
        return dashboardService;
    }

    public ProductDAO getProductDAO() {
        return this.productDAO; // Now this works because productDAO is a class field!
    }
}