package com.UNZ.Biblioteque.repositories;

import com.UNZ.Biblioteque.models.Emprunt;
import com.UNZ.Biblioteque.models.StatutEmprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpruntRepository 
    extends JpaRepository<Emprunt, Long> {

    List<Emprunt> findByEtudiantId(Long etudiantId);
    List<Emprunt> findByStatut(StatutEmprunt statut);
    List<Emprunt> findByEtudiantIdAndStatut(
        Long etudiantId, StatutEmprunt statut);
    Integer countByEtudiantIdAndStatut(
        Long etudiantId, StatutEmprunt statut);
}