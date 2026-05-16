package com.UNZ.Biblioteque;

import com.UNZ.Biblioteque.models.*;
import com.UNZ.Biblioteque.repositories.*;
import com.UNZ.Biblioteque.services.EmpruntService;
import com.UNZ.Biblioteque.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpruntServiceTest {

    @Mock
    private EmpruntRepository empruntRepository;
    @Mock
    private OuvrageRepository ouvrageRepository;
    @Mock
    private EtudiantRepository etudiantRepository;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private EmpruntService empruntService;

    private Etudiant etudiant;
    private Ouvrage ouvrage;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setNom("Barry");
        etudiant.setPrenom("Roukietou");
        etudiant.setEmail("roukietou@unz.bf");
        etudiant.setMatricule("ETU001");
        etudiant.setFiliere("Informatique");
        etudiant.setNiveau("L3");
        etudiant.setRole(Role.ETUDIANT);
        etudiant.setActif(true);

        ouvrage = new Ouvrage();
        ouvrage.setId(1L);
        ouvrage.setTitre("Java pour debutants");
        ouvrage.setAuteur("James Gosling");
        ouvrage.setIsbn("978-1234567890");
        ouvrage.setNombreExemplaires(5);
        ouvrage.setExemplairesDisponibles(3);
    }

    @Test
    void testCreerEmprunt_LimiteAtteinte() {
        when(etudiantRepository.findById(1L))
            .thenReturn(Optional.of(etudiant));
        when(ouvrageRepository.findById(1L))
            .thenReturn(Optional.of(ouvrage));
        when(empruntRepository.countByEtudiantIdAndStatut(
            1L, StatutEmprunt.EN_COURS)).thenReturn(2);
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> empruntService.creerEmprunt(1L, 1L));
        assertEquals("Limite de 2 emprunts atteinte",
            exception.getMessage());
    }

    @Test
    void testCreerEmprunt_OuvrageIndisponible() {
        ouvrage.setExemplairesDisponibles(0);
        when(etudiantRepository.findById(1L))
            .thenReturn(Optional.of(etudiant));
        when(ouvrageRepository.findById(1L))
            .thenReturn(Optional.of(ouvrage));
        when(empruntRepository.countByEtudiantIdAndStatut(
            1L, StatutEmprunt.EN_COURS)).thenReturn(0);
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> empruntService.creerEmprunt(1L, 1L));
        assertEquals("Ouvrage non disponible",
            exception.getMessage());
    }

    @Test
    void testEstEnRetard_Vrai() {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateRetourPrevue(LocalDate.now().minusDays(3));
        assertTrue(emprunt.estEnRetard());
    }

    @Test
    void testEstEnRetard_Faux() {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(3));
        assertFalse(emprunt.estEnRetard());
    }

    @Test
    void testCalculerPenalite() {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateRetourPrevue(LocalDate.now().minusDays(5));
        double penalite = emprunt.calculerPenalite();
        assertEquals(500.0, penalite);
    }
}
