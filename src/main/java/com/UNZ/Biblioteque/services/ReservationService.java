package com.UNZ.Biblioteque.services;

import com.UNZ.Biblioteque.models.*;
import com.UNZ.Biblioteque.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private OuvrageRepository ouvrageRepository;

    @Autowired
    private NotificationService notificationService;

    public Reservation creerReservation(
        Long etudiantId, Long ouvrageId) {

        Optional<Reservation> existante =
            reservationRepository
                .findByEtudiantIdAndOuvrageId(
                    etudiantId, ouvrageId);

        if (existante.isPresent()) {
            throw new RuntimeException(
                "Vous avez déjà réservé cet ouvrage");
        }

        Etudiant etudiant = etudiantRepository
            .findById(etudiantId)
            .orElseThrow(() -> new RuntimeException(
                "Étudiant non trouvé"));

        Ouvrage ouvrage = ouvrageRepository
            .findById(ouvrageId)
            .orElseThrow(() -> new RuntimeException(
                "Ouvrage non trouvé"));

        List<Reservation> reservationsOuvrage =
            reservationRepository.findByOuvrageId(ouvrageId);

        Reservation reservation = new Reservation();
        reservation.setEtudiant(etudiant);
        reservation.setOuvrage(ouvrage);
        reservation.setDateReservation(LocalDate.now());
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        reservation.setPosition(
            reservationsOuvrage.size() + 1);

        Reservation saved =
            reservationRepository.save(reservation);

        notificationService.envoyerNotification(
            etudiant,
            "Réservation confirmée pour : " +
            ouvrage.getTitre() +
            " — Position : " + reservation.getPosition(),
            TypeNotification.DISPONIBILITE
        );

        return saved;
    }

    public List<Reservation> getReservationsEtudiant(
        Long etudiantId) {
        return reservationRepository
            .findByEtudiantId(etudiantId);
    }

    public void annulerReservation(Long reservationId) {
        Reservation reservation = reservationRepository
            .findById(reservationId)
            .orElseThrow(() -> new RuntimeException(
                "Réservation non trouvée"));
        reservation.annuler();
        reservationRepository.save(reservation);
    }
}