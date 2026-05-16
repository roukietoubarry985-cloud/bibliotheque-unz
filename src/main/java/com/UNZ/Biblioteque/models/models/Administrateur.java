package com.UNZ.Biblioteque.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "administrateurs")
public class Administrateur extends Utilisateur {

    @Column(nullable = false)
    private String niveau;
}