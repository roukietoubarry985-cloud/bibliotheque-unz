package com.UNZ.Biblioteque;

import com.UNZ.Biblioteque.models.*;
import com.UNZ.Biblioteque.repositories.OuvrageRepository;
import com.UNZ.Biblioteque.services.OuvrageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OuvrageServiceTest {

    @Mock
    private OuvrageRepository ouvrageRepository;

    @InjectMocks
    private OuvrageService ouvrageService;

    private Ouvrage ouvrage1;
    private Ouvrage ouvrage2;

    @BeforeEach
    void setUp() {
        Categorie categorie = new Categorie();
        categorie.setId(1L);
        categorie.setNom("Informatique");
        categorie.setRayon("A1");

        ouvrage1 = new Ouvrage();
        ouvrage1.setId(1L);
        ouvrage1.setTitre("Java pour debutants");
        ouvrage1.setAuteur("James Gosling");
        ouvrage1.setIsbn("978-001");
        ouvrage1.setNombreExemplaires(5);
        ouvrage1.setExemplairesDisponibles(3);
        ouvrage1.setCategorie(categorie);

        ouvrage2 = new Ouvrage();
        ouvrage2.setId(2L);
        ouvrage2.setTitre("Spring Boot");
        ouvrage2.setAuteur("Craig Walls");
        ouvrage2.setIsbn("978-002");
        ouvrage2.setNombreExemplaires(3);
        ouvrage2.setExemplairesDisponibles(0);
        ouvrage2.setCategorie(categorie);
    }

    @Test
    void testGetTousLesOuvrages() {
        when(ouvrageRepository.findAll())
            .thenReturn(Arrays.asList(ouvrage1, ouvrage2));
        List<Ouvrage> ouvrages = ouvrageService.getTousLesOuvrages();
        assertEquals(2, ouvrages.size());
        verify(ouvrageRepository, times(1)).findAll();
    }

    @Test
    void testGetOuvrageParId_Trouve() {
        when(ouvrageRepository.findById(1L))
            .thenReturn(Optional.of(ouvrage1));
        Optional<Ouvrage> result = ouvrageService.getOuvrageParId(1L);
        assertTrue(result.isPresent());
        assertEquals("Java pour debutants", result.get().getTitre());
    }

    @Test
    void testGetOuvrageParId_NonTrouve() {
        when(ouvrageRepository.findById(99L))
            .thenReturn(Optional.empty());
        Optional<Ouvrage> result = ouvrageService.getOuvrageParId(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testAjouterOuvrage() {
        when(ouvrageRepository.save(any(Ouvrage.class)))
            .thenReturn(ouvrage1);
        Ouvrage result = ouvrageService.ajouterOuvrage(ouvrage1);
        assertNotNull(result);
        verify(ouvrageRepository, times(1)).save(any(Ouvrage.class));
    }

    @Test
    void testSupprimerOuvrage() {
        doNothing().when(ouvrageRepository).deleteById(1L);
        ouvrageService.supprimerOuvrage(1L);
        verify(ouvrageRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEstDisponible_Vrai() {
        assertTrue(ouvrage1.estDisponible());
    }

    @Test
    void testEstDisponible_Faux() {
        assertFalse(ouvrage2.estDisponible());
    }

    @Test
    void testRechercherParTitre() {
        when(ouvrageRepository.findByTitreContaining("Java"))
            .thenReturn(Arrays.asList(ouvrage1));
        List<Ouvrage> result = ouvrageService.rechercherParTitre("Java");
        assertEquals(1, result.size());
    }
}
