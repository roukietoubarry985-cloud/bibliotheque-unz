package com.UNZ.Biblioteque.models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
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
    private LocalDate dateReservation;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutReservation statut;
    @Column(nullable = false)
    private Integer position;
    public void annuler() { this.statut = StatutReservation.ANNULEE; }
    public void confirmer() { this.statut = StatutReservation.CONFIRMEE; }
}
