package com.UNZ.Biblioteque.repositories;

import com.UNZ.Biblioteque.models.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OuvrageRepository 
    extends JpaRepository<Ouvrage, Long> {

    List<Ouvrage> findByTitreContaining(String titre);
    List<Ouvrage> findByAuteurContaining(String auteur);
    Optional<Ouvrage> findByIsbn(String isbn);
    List<Ouvrage> findByCategorieId(Long categorieId);
    List<Ouvrage> findByExemplairesDisponiblesGreaterThan(
        Integer nombre);
}