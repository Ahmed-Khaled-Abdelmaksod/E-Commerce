package gov.iti.jets.ecommerce.context;

import com.zaxxer.hikari.HikariDataSource;

import gov.iti.jets.ecommerce.config.DataSourceConfig;

// --- DAO Imports ---

import gov.iti.jets.ecommerce.dao.AdminDashboardDAO;


import gov.iti.jets.ecommerce.dao.impl.AdminDashboardDaoImpl;

// --- Service Imports ---

import gov.iti.jets.ecommerce.service.AdminDashboardService; 

import gov.iti.jets.ecommerce.service.impl.AdminDashboardServiceImpl;

public class ServiceLocator {

    private static volatile ServiceLocator instance;


    private final AdminDashboardService adminDashboardService;

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

        // DAO layer

        AdminDashboardDAO adminDashboardDAO = new AdminDashboardDaoImpl(dataSource);

        // Service layer

        this.adminDashboardService = new AdminDashboardServiceImpl(adminDashboardDAO);
    }

    // --- Getters ---

    public AdminDashboardService getAdminDashboardService() {
        return adminDashboardService;
    }
}