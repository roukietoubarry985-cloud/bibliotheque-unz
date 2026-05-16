package com.UNZ.Biblioteque.services;

import com.UNZ.Biblioteque.models.*;
import com.UNZ.Biblioteque.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private OuvrageRepository ouvrageRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private NotificationService notificationService;

    public Emprunt creerEmprunt(Long etudiantId, Long ouvrageId) {

        Etudiant etudiant = etudiantRepository
            .findById(etudiantId)
            .orElseThrow(() -> new RuntimeException(
                "Étudiant non trouvé"));

        Ouvrage ouvrage = ouvrageRepository
            .findById(ouvrageId)
            .orElseThrow(() -> new RuntimeException(
                "Ouvrage non trouvé"));

        Integer empruntsEnCours = empruntRepository
            .countByEtudiantIdAndStatut(
                etudiantId, StatutEmprunt.EN_COURS);

        if (empruntsEnCours >= 2) {
            throw new RuntimeException(
                "Limite de 2 emprunts atteinte");
        }

        if (!ouvrage.estDisponible()) {
            throw new RuntimeException(
                "Ouvrage non disponible");
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setEtudiant(etudiant);
        emprunt.setOuvrage(ouvrage);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(
            LocalDate.now().plusDays(14));
        emprunt.setStatut(StatutEmprunt.EN_COURS);

        ouvrage.setExemplairesDisponibles(
            ouvrage.getExemplairesDisponibles() - 1);
        ouvrageRepository.save(ouvrage);

        Emprunt savedEmprunt = empruntRepository.save(emprunt);

        notificationService.envoyerNotification(
            etudiant,
            "Emprunt confirmé : " + ouvrage.getTitre() +
            " — Retour prévu le " + emprunt.getDateRetourPrevue(),
            TypeNotification.RAPPEL_RETOUR
        );

        return savedEmprunt;
    }

    public Emprunt traiterRetour(Long empruntId) {

        Emprunt emprunt = empruntRepository
            .findById(empruntId)
            .orElseThrow(() -> new RuntimeException(
                "Emprunt non trouvé"));

        emprunt.setDateRetourReelle(LocalDate.now());
        emprunt.setStatut(StatutEmprunt.RETOURNE);

        Ouvrage ouvrage = emprunt.getOuvrage();
        ouvrage.setExemplairesDisponibles(
            ouvrage.getExemplairesDisponibles() + 1);
        ouvrageRepository.save(ouvrage);

        return empruntRepository.save(emprunt);
    }

    public List<Emprunt> getEmpruntsParEtudiant(Long etudiantId) {
        return empruntRepository.findByEtudiantId(etudiantId);
    }

    public List<Emprunt> getTousLesEmprunts() {
        return empruntRepository.findAll();
    }
}