package com.UNZ.Biblioteque.models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emprunts")
@JsonIgnoreProperties({"penalite"})
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonIgnoreProperties({"emprunts","reservations","notifications"})
    private Etudiant etudiant;
    @ManyToOne
    @JoinColumn(name = "ouvrage_id", nullable = false)
    @JsonIgnoreProperties({"emprunts","reservations"})
    private Ouvrage ouvrage;
    @Column(nullable = false)
    private LocalDate dateEmprunt;
    @Column(nullable = false)
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourReelle;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEmprunt statut;
    @OneToOne(mappedBy = "emprunt", cascade = CascadeType.ALL)
    private Penalite penalite;
    public boolean estEnRetard() {
        if (dateRetourReelle != null) return dateRetourReelle.isAfter(dateRetourPrevue);
        return LocalDate.now().isAfter(dateRetourPrevue);
    }
    public double calculerPenalite() {
        if (!estEnRetard()) return 0;
        LocalDate dateFin = dateRetourReelle != null ? dateRetourReelle : LocalDate.now();
        long joursRetard = ChronoUnit.DAYS.between(dateRetourPrevue, dateFin);
        return joursRetard * 100.0;
    }
}
