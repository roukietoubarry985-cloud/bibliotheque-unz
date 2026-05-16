package com.UNZ.Biblioteque.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "penalites")
public class Penalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "emprunt_id", nullable = false)
    private Emprunt emprunt;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    private LocalDate dateApplication;

    @Column(nullable = false)
    private Boolean estPayee = false;

    public void payer() {
        this.estPayee = true;
    }

    public Double calculer(long joursRetard) {
        return joursRetard * 100.0;
    }
}