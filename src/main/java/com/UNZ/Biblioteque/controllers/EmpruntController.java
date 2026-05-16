package com.UNZ.Biblioteque.controllers;

import com.UNZ.Biblioteque.models.Emprunt;
import com.UNZ.Biblioteque.services.EmpruntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emprunts")
@CrossOrigin(origins = "*")
public class EmpruntController {

    @Autowired
    private EmpruntService empruntService;

    @GetMapping
    public ResponseEntity<List<Emprunt>> getTousLesEmprunts() {
        return ResponseEntity.ok(
            empruntService.getTousLesEmprunts());
    }

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Emprunt>> getEmpruntsEtudiant(
        @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
            empruntService.getEmpruntsParEtudiant(etudiantId));
    }

    @PostMapping
    public ResponseEntity<?> creerEmprunt(
        @RequestBody Map<String, Long> request) {
        try {
            Long etudiantId = request.get("etudiantId");
            Long ouvrageId = request.get("ouvrageId");
            Emprunt emprunt = empruntService
                .creerEmprunt(etudiantId, ouvrageId);
            return ResponseEntity.ok(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(e.getMessage());
        }
    }

    @PutMapping("/{id}/retour")
    public ResponseEntity<?> traiterRetour(
        @PathVariable Long id) {
        try {
            Emprunt emprunt = empruntService.traiterRetour(id);
            return ResponseEntity.ok(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(e.getMessage());
        }
    }
}