package gov.iti.jets.ecommerce.service.impl;

import gov.iti.jets.ecommerce.beans.dashboard.*;
import gov.iti.jets.ecommerce.dao.*;
import gov.iti.jets.ecommerce.entity.*;
import gov.iti.jets.ecommerce.enums.UserRole;
import gov.iti.jets.ecommerce.service.DashboardService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardServiceImpl implements DashboardService {

    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final UserDAO userDAO;

    public DashboardServiceImpl(ProductDAO productDAO, OrderDAO orderDAO, 
                                OrderItemDAO orderItemDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<ProductBean> getAllProductsForDashboard() {
        return productDAO.findAll().stream()
                .map(this::mapToProductBean)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderBean> getAllOrdersForDashboard() {
        List<Order> orders = orderDAO.findAll();
        List<OrderBean> orderBeans = new ArrayList<>();

        for (Order order : orders) {
            OrderBean bean = new OrderBean();
            bean.setOrderId(order.getOrderId());
            bean.setTotalPrice(order.getTotalPrice());
            
            if (order.getOrderDate() != null) {
                bean.setOrderDate(java.sql.Timestamp.valueOf(order.getOrderDate()));
            }
            
            if (order.getUser() != null) {
                bean.setCustomerName(order.getUser().getFullName());
            }

            List<OrderItemBean> itemBeans = orderItemDAO.findByOrderId(order.getOrderId())
                    .stream()
                    .map(itemEntity -> {
                        OrderItemBean itemBean = new OrderItemBean();
                        itemBean.setProductName(itemEntity.getProduct().getName());
                        itemBean.setQuantity(itemEntity.getQuantity());
                        itemBean.setPrice(itemEntity.getPrice());
                        return itemBean;
                    }).collect(Collectors.toList());

            bean.setOrderItems(itemBeans);
            orderBeans.add(bean);
        }
        return orderBeans;
    }

    @Override
    public List<CustomerBean> getAllCustomersForDashboard() {
        return userDAO.findByRole(UserRole.CUSTOMER).stream()
                .map(this::mapToCustomerBean)
                .collect(Collectors.toList());
    }

    // --- Private Helper Mapping Methods ---

    private ProductBean mapToProductBean(Product entity) {
        ProductBean bean = new ProductBean();
        bean.setProductId(entity.getProductId());
        bean.setName(entity.getName());
        bean.setPrice(entity.getPrice());
        bean.setStockQuantity(entity.getStockQuantity());
        bean.setImageUrl(entity.getImageUrl());
        if (entity.getCategory() != null) {
            bean.setCategoryName(entity.getCategory().getName());
        }
        return bean;
    }

    private CustomerBean mapToCustomerBean(User entity) {
        CustomerBean bean = new CustomerBean();
        bean.setUserId(entity.getUserId());
        bean.setFullName(entity.getFullName());
        bean.setEmail(entity.getEmail());
        bean.setPhone(entity.getPhone());
        bean.setAddress(entity.getAddress());
        bean.setCreditBalance(entity.getCreditBalance());
        return bean;
    }
}