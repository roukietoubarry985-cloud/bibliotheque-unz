package com.UNZ.Biblioteque.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "etudiants")
public class Etudiant extends Utilisateur {

    @Column(nullable = false, unique = true)
    private String matricule;

    @Column(nullable = false)
    private String filiere;

    @Column(nullable = false)
    private String niveau;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Emprunt> emprunts;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Notification> notifications;
}