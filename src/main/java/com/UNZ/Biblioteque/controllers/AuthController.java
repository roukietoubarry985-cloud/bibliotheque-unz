package com.UNZ.Biblioteque.controllers;

import com.UNZ.Biblioteque.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String motDePasse = request.get("motDePasse");
            Map<String, String> response =
                authService.login(email, motDePasse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(e.getMessage());
        }
    }
}
