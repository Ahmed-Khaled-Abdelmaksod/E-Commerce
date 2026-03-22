package gov.iti.jets.ecommerce.context;

// --- DAO Imports ---
import gov.iti.jets.ecommerce.dao.*;
import gov.iti.jets.ecommerce.dao.impl.*;

// --- Service Imports ---
import gov.iti.jets.ecommerce.service.DashboardService;
import gov.iti.jets.ecommerce.service.impl.DashboardServiceImpl;

/**
 * ServiceLocator follows the Singleton pattern to provide centralized 
 * access to application services and manages the lifecycle of DAOs.
 */
public class ServiceLocator {

    private static volatile ServiceLocator instance;

    // Services (The only entry points for the Web Layer)
    private final DashboardService dashboardService;

    // DAOs (Internal dependencies for Services, no public getters)
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final UserDAO userDAO;

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
        // 1. Initialize DAOs as stateless instances
        this.productDAO = new ProductDaoImpl();
        this.categoryDAO = new CategoryDaoImpl();
        this.orderDAO = new OrderDaoImpl();
        this.orderItemDAO = new OrderItemDaoImpl();
        this.userDAO = new UserDaoImpl();

        // 2. Inject DAOs into Services
        this.dashboardService = new DashboardServiceImpl(
                productDAO,
                orderDAO,
                orderItemDAO,
                userDAO,
                categoryDAO
        );
    }

    // --- Dao Getters (should be removed) ---

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    // --- Service Getters ---
    
    /**
     * @return The singleton instance of DashboardService
     */
    public DashboardService getDashboardService() {
        return dashboardService;
    }

    // Note: DAOs are kept private to enforce using the Service Layer 
    // for all database interactions.
}