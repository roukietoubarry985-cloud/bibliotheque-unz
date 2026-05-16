package com.UNZ.Biblioteque.repositories;

import com.UNZ.Biblioteque.models.Penalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PenaliteRepository 
    extends JpaRepository<Penalite, Long> {

    List<Penalite> findByEmpruntEtudiantId(Long etudiantId);
    List<Penalite> findByEstPayee(Boolean estPayee);
}