package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.CategorieDAO;
import gov.iti.jets.ecommerce.entity.Categorie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class CategorieDaoImpl implements CategorieDAO {

    public CategorieDaoImpl() {

    }

    @Override
    public Categorie insert(Categorie categorie) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(categorie);
            transaction.commit();
            return categorie;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to insert category", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Categorie> findAll() {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            return em.createQuery("SELECT c FROM Categorie c", Categorie.class).getResultList();
        }
    }

    @Override
    public Optional<Categorie> findById(int id) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Categorie categorie = em.find(Categorie.class, Integer.valueOf(id));
            return Optional.ofNullable(categorie);
        }
    }

    @Override
    public boolean update(Categorie categorie) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(categorie);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            // Using Integer.valueOf(id) here as well
            Categorie categorie = em.find(Categorie.class, Integer.valueOf(id));
            if (categorie != null) {
                em.remove(categorie);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            return false;
        } finally {
            em.close();
        }
    }
}