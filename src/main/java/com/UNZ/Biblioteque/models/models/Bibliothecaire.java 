package com.UNZ.Biblioteque.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bibliothecaires")
public class Bibliothecaire extends Utilisateur {

    @Column(nullable = false, unique = true)
    private String matricule;

    @Column(nullable = false)
    private String departement;
}