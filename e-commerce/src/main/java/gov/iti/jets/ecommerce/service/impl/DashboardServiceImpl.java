package gov.iti.jets.ecommerce.service.impl;

import gov.iti.jets.ecommerce.beans.dashboard.*;
import gov.iti.jets.ecommerce.DTO.*;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.*;
import gov.iti.jets.ecommerce.entity.*;
import gov.iti.jets.ecommerce.enums.UserRole;
import gov.iti.jets.ecommerce.mappers.*;
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
    private final CategoryDAO categoryDAO;

    public DashboardServiceImpl(ProductDAO productDAO, OrderDAO orderDAO,
                                OrderItemDAO orderItemDAO, UserDAO userDAO,
                                CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public void saveProduct(ProductBean bean) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            
            Product product;
            if (bean.getProductId() != null && bean.getProductId() > 0) {
                // Edit
                product = productDAO.findById(em, bean.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
            } else {
                // Add
                product = new Product();
            }

            product.setName(bean.getName());
            product.setDescription(bean.getDescription());
            product.setPrice(bean.getPrice());
            product.setStockQuantity(bean.getStockQuantity());
            product.setImageUrl(bean.getImageUrl());
            product.setHighlighted(bean.isHighlighted());

            if (bean.getCategoryId() != null) {
                Category category = em.find(Category.class, bean.getCategoryId());
                product.setCategory(category);
            }

            if (product.getProductId() == 0) {
                productDAO.insert(em, product);
            } else {
                productDAO.update(em, product);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error saving product: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            boolean deleted = productDAO.delete(em, productId);
            em.getTransaction().commit();
            return deleted;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProductBean> getAllProductsForDashboard() {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            List<Product> products = productDAO.findAll(em);
            return products.stream()
                    .map(ProductMapper::toBean) 
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in Service - getAllProductsForDashboard: " + e.getMessage());
            e.printStackTrace();
            throw e; 
        }
    }

    @Override
    public List<CustomerBean> getAllCustomersForDashboard() {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            List<User> customers = userDAO.findByRole(em, UserRole.CUSTOMER);
            System.out.println("Service DEBUG: Found " + (customers != null ? customers.size() : 0) + " customers");
            
            if (customers == null) return new ArrayList<>();

            return customers.stream()
                    .map(CustomerMapper::toBean)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in Service - getAllCustomersForDashboard: " + e.getMessage());
            e.printStackTrace(); 
            throw e;
        }
    }

    @Override
    public List<OrderBean> getAllOrdersForDashboard() {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            List<Order> orders = orderDAO.findAll(em);
            List<OrderBean> orderBeans = new ArrayList<>();
            
            System.out.println("Service DEBUG: Processing " + (orders != null ? orders.size() : 0) + " orders");

            if (orders != null) {
                for (Order order : orders) {
                    OrderBean bean = OrderMapper.toBean(order);
                    
                    List<OrderItemBean> itemBeans = orderItemDAO.findByOrderId(em, order.getOrderId())
                            .stream()
                            .map(OrderMapper::toItemBean)
                            .collect(Collectors.toList());

                    bean.setOrderItems(itemBeans);
                    orderBeans.add(bean);
                }
            }
            return orderBeans;
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in Service - getAllOrdersForDashboard: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<CategoryDTO> getAllCategoriesForDashboard() {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            List<Category> categories = categoryDAO.findAll(em);
            System.out.println("Service DEBUG: Found " + (categories != null ? categories.size() : 0) + " categories");

            return categories.stream()
                    .map(cat -> {
                        CategoryDTO dto = new CategoryDTO();
                        dto.setCategoryId(cat.getCategoryId());
                        dto.setName(cat.getName());
                        return dto;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in Service - getAllCategoriesForDashboard: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}