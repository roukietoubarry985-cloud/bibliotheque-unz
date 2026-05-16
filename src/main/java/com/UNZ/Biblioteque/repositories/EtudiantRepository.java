package com.UNZ.Biblioteque.repositories;

import com.UNZ.Biblioteque.models.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EtudiantRepository 
    extends JpaRepository<Etudiant, Long> {

    Optional<Etudiant> findByEmail(String email);
    Optional<Etudiant> findByMatricule(String matricule);
    Boolean existsByMatricule(String matricule);
}