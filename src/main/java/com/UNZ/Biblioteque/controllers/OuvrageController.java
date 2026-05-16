package com.UNZ.Biblioteque.controllers;

import com.UNZ.Biblioteque.models.Ouvrage;
import com.UNZ.Biblioteque.services.OuvrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ouvrages")
@CrossOrigin(origins = "*")
public class OuvrageController {

    @Autowired
    private OuvrageService ouvrageService;

    @GetMapping
    public ResponseEntity<List<Ouvrage>> getTousLesOuvrages() {
        return ResponseEntity.ok(
            ouvrageService.getTousLesOuvrages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ouvrage> getOuvrageParId(
        @PathVariable Long id) {
        return ouvrageService.getOuvrageParId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recherche/titre")
    public ResponseEntity<List<Ouvrage>> rechercherParTitre(
        @RequestParam String titre) {
        return ResponseEntity.ok(
            ouvrageService.rechercherParTitre(titre));
    }

    @GetMapping("/recherche/auteur")
    public ResponseEntity<List<Ouvrage>> rechercherParAuteur(
        @RequestParam String auteur) {
        return ResponseEntity.ok(
            ouvrageService.rechercherParAuteur(auteur));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Ouvrage>> getDisponibles() {
        return ResponseEntity.ok(
            ouvrageService.getOuvragesDisponibles());
    }

    @PostMapping
    public ResponseEntity<Ouvrage> ajouterOuvrage(
        @RequestBody Ouvrage ouvrage) {
        return ResponseEntity.ok(
            ouvrageService.ajouterOuvrage(ouvrage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ouvrage> modifierOuvrage(
        @PathVariable Long id,
        @RequestBody Ouvrage ouvrage) {
        return ResponseEntity.ok(
            ouvrageService.modifierOuvrage(id, ouvrage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerOuvrage(
        @PathVariable Long id) {
        ouvrageService.supprimerOuvrage(id);
        return ResponseEntity.ok().build();
    }
}