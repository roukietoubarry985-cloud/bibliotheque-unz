package com.UNZ.Biblioteque.models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ouvrages")
@JsonIgnoreProperties({"emprunts","reservations"})
public class Ouvrage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titre;
    @Column(nullable = false)
    private String auteur;
    @Column(nullable = false, unique = true)
    private String isbn;
    private Integer annee;
    @Column(nullable = false)
    private Integer nombreExemplaires;
    @Column(nullable = false)
    private Integer exemplairesDisponibles;
    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    @JsonIgnoreProperties({"ouvrages"})
    private Categorie categorie;
    @OneToMany(mappedBy = "ouvrage", cascade = CascadeType.ALL)
    private List<Emprunt> emprunts;
    @OneToMany(mappedBy = "ouvrage", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
    public boolean estDisponible() {
        return this.exemplairesDisponibles > 0;
    }
}
