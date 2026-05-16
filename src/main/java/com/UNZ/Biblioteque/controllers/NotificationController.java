package com.UNZ.Biblioteque.controllers;

import com.UNZ.Biblioteque.models.Notification;
import com.UNZ.Biblioteque.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Notification>> getNotifications(
        @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
            notificationService
                .getNotificationsEtudiant(etudiantId));
    }

    @GetMapping("/etudiant/{etudiantId}/nonlues")
    public ResponseEntity<List<Notification>> getNonLues(
        @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
            notificationService
                .getNotificationsNonLues(etudiantId));
    }

    @PutMapping("/{id}/lue")
    public ResponseEntity<?> marquerCommeLue(
        @PathVariable Long id) {
        try {
            Notification notification = notificationService
                .marquerCommeLue(id);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(e.getMessage());
        }
    }
}