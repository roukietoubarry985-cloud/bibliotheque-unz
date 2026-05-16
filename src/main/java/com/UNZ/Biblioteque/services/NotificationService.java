package com.UNZ.Biblioteque.services;

import com.UNZ.Biblioteque.models.*;
import com.UNZ.Biblioteque.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification envoyerNotification(
        Etudiant etudiant,
        String message,
        TypeNotification type) {

        Notification notification = new Notification();
        notification.setEtudiant(etudiant);
        notification.setMessage(message);
        notification.setDateEnvoi(LocalDateTime.now());
        notification.setEstLue(false);
        notification.setType(type);

        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsEtudiant(
        Long etudiantId) {
        return notificationRepository
            .findByEtudiantId(etudiantId);
    }

    public List<Notification> getNotificationsNonLues(
        Long etudiantId) {
        return notificationRepository
            .findByEtudiantIdAndEstLue(etudiantId, false);
    }

    public Notification marquerCommeLue(Long notificationId) {
        Notification notification = notificationRepository
            .findById(notificationId)
            .orElseThrow(() -> new RuntimeException(
                "Notification non trouvée"));
        notification.marquerCommeLue();
        return notificationRepository.save(notification);
    }
}