package com.UNZ.Biblioteque.services;

import com.UNZ.Biblioteque.models.Utilisateur;
import com.UNZ.Biblioteque.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private BCryptPasswordEncoder passwordEncoder =
        new BCryptPasswordEncoder();

    public Map<String, String> login(
        String email, String motDePasse) {

        Utilisateur utilisateur = utilisateurRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException(
                "Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(
            motDePasse, utilisateur.getMotDePasse())) {
            throw new RuntimeException(
                "Email ou mot de passe incorrect");
        }

        if (!utilisateur.getActif()) {
            throw new RuntimeException("Compte desactive");
        }

        Map<String, String> response = new HashMap<>();
        response.put("id", utilisateur.getId().toString());
        response.put("email", utilisateur.getEmail());
        response.put("nom", utilisateur.getNom());
        response.put("prenom", utilisateur.getPrenom());
        response.put("role", utilisateur.getRole().toString());
        return response;
    }
}
