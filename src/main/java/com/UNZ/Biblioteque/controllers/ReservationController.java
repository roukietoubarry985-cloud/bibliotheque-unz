package com.UNZ.Biblioteque.controllers;

import com.UNZ.Biblioteque.models.Reservation;
import com.UNZ.Biblioteque.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Reservation>> getReservations(
        @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
            reservationService
                .getReservationsEtudiant(etudiantId));
    }

    @PostMapping
    public ResponseEntity<?> creerReservation(
        @RequestBody Map<String, Long> request) {
        try {
            Long etudiantId = request.get("etudiantId");
            Long ouvrageId = request.get("ouvrageId");
            Reservation reservation = reservationService
                .creerReservation(etudiantId, ouvrageId);
            return ResponseEntity.ok(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> annulerReservation(
        @PathVariable Long id) {
        try {
            reservationService.annulerReservation(id);
            return ResponseEntity.ok()
                .body("Réservation annulée");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(e.getMessage());
        }
    }
}