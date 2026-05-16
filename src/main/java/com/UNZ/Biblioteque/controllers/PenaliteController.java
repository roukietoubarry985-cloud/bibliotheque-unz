package com.UNZ.Biblioteque.controllers;

import com.UNZ.Biblioteque.models.Penalite;
import com.UNZ.Biblioteque.services.PenaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/penalites")
@CrossOrigin(origins = "*")
public class PenaliteController {

    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Penalite>> getPenalites(
        @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
            penaliteService.getPenalitesEtudiant(etudiantId));
    }

    @PostMapping("/{empruntId}/appliquer")
    public ResponseEntity<?> appliquerPenalite(
        @PathVariable Long empruntId) {
        try {
            Penalite penalite = penaliteService
                .appliquerPenalite(empruntId);
            return ResponseEntity.ok(penalite);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(e.getMessage());
        }
    }

    @PutMapping("/{id}/payer")
    public ResponseEntity<?> payerPenalite(
        @PathVariable Long id) {
        try {
            Penalite penalite = penaliteService
                .payerPenalite(id);
            return ResponseEntity.ok(penalite);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(e.getMessage());
        }
    }
}