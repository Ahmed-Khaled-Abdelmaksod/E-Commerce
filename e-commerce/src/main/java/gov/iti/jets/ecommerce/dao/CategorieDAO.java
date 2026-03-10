package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Categorie;

import java.util.List;
import java.util.Optional;

public interface CategorieDAO {

    Categorie insert(Categorie categorie);

    List<Categorie> findAll();

    Optional<Categorie> findById(int id);

    boolean update(Categorie categorie);

    boolean delete(int id);
}