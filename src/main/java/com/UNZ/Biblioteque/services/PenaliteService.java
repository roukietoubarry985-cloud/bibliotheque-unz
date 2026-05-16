package com.UNZ.Biblioteque.services;

import com.UNZ.Biblioteque.models.*;
import com.UNZ.Biblioteque.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PenaliteService {

    @Autowired
    private PenaliteRepository penaliteRepository;

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private NotificationService notificationService;

    public Penalite appliquerPenalite(Long empruntId) {

        Emprunt emprunt = empruntRepository
            .findById(empruntId)
            .orElseThrow(() -> new RuntimeException(
                "Emprunt non trouvé"));

        if (!emprunt.estEnRetard()) {
            throw new RuntimeException(
                "Pas de retard pour cet emprunt");
        }

        double montant = emprunt.calculerPenalite();

        Penalite penalite = new Penalite();
        penalite.setEmprunt(emprunt);
        penalite.setMontant(montant);
        penalite.setDateApplication(LocalDate.now());
        penalite.setEstPayee(false);

        Penalite saved = penaliteRepository.save(penalite);

        notificationService.envoyerNotification(
            emprunt.getEtudiant(),
            "Pénalité de " + montant +
            " FCFA appliquée pour retard — " +
            emprunt.getOuvrage().getTitre(),
            TypeNotification.PENALITE
        );

        return saved;
    }

    public Penalite payerPenalite(Long penaliteId) {
        Penalite penalite = penaliteRepository
            .findById(penaliteId)
            .orElseThrow(() -> new RuntimeException(
                "Pénalité non trouvée"));
        penalite.payer();
        return penaliteRepository.save(penalite);
    }

    public List<Penalite> getPenalitesEtudiant(
        Long etudiantId) {
        return penaliteRepository
            .findByEmpruntEtudiantId(etudiantId);
    }
}