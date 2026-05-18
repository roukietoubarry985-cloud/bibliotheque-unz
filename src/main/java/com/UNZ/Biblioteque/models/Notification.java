package com.UNZ.Biblioteque.models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
@JsonIgnoreProperties({"etudiant"})
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private LocalDateTime dateEnvoi;
    @Column(nullable = false)
    private Boolean estLue = false;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeNotification type;
    public void marquerCommeLue() { this.estLue = true; }
}
