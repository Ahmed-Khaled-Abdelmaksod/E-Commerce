package gov.iti.jets.ecommerce.service.impl;

import gov.iti.jets.ecommerce.beans.dashboard.*;
import gov.iti.jets.ecommerce.DTO.*;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.*;
import gov.iti.jets.ecommerce.entity.*;
import gov.iti.jets.ecommerce.enums.UserRole;
import gov.iti.jets.ecommerce.service.DashboardService;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardServiceImpl implements DashboardService {

    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final UserDAO userDAO;
    private final CategoryDAO categorieDAO;

    public DashboardServiceImpl(ProductDAO productDAO, OrderDAO orderDAO,
                                OrderItemDAO orderItemDAO, UserDAO userDAO,
                                CategoryDAO categorieDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.userDAO = userDAO;
        this.categorieDAO = categorieDAO;
    }

    @Override
    public List<ProductDTO> getAllProductsForDashboard() {
        return productDAO.findAll().stream()
                .map(this::mapToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllCategoriesForDashboard() {
        return categorieDAO.findAll().stream().map(cat -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setCategoryId(cat.getCategoryId());
            dto.setName(cat.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    private ProductDTO mapToProductDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(entity.getProductId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setImageUrl(entity.getImageUrl());
        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getCategoryId());
            dto.setCategoryName(entity.getCategory().getName());
        }
        return dto;
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
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        return userDAO.findByRole(em,UserRole.CUSTOMER).stream()
                .map(this::mapToCustomerBean)
                .collect(Collectors.toList());
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