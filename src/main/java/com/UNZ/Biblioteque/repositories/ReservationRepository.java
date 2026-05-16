package com.UNZ.Biblioteque.repositories;

import com.UNZ.Biblioteque.models.Reservation;
import com.UNZ.Biblioteque.models.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository 
    extends JpaRepository<Reservation, Long> {

    List<Reservation> findByEtudiantId(Long etudiantId);
    List<Reservation> findByOuvrageId(Long ouvrageId);
    Optional<Reservation> findByEtudiantIdAndOuvrageId(
        Long etudiantId, Long ouvrageId);
    List<Reservation> findByStatut(StatutReservation statut);
}