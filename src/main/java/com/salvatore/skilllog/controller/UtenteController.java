package com.salvatore.skilllog.controller;

import com.salvatore.skilllog.dto.UtenteRequest;
import com.salvatore.skilllog.dto.UtenteResponse;
import com.salvatore.skilllog.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final UtenteService service;

    public UtenteController(UtenteService service) {
        this.service = service;
    }

    @GetMapping
    public List<UtenteResponse> getAllUtenti() {
        return service.getAllUtenti();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtenteResponse> getUtenteById(@PathVariable Long id) {
        UtenteResponse utente = service.getUtenteById(id);
        return utente == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(utente);
    }

    @PostMapping
    public ResponseEntity<UtenteResponse> createUtente(@Valid @RequestBody UtenteRequest request) {
        return ResponseEntity.ok(service.createUtente(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtenteResponse> updateUtente(
            @PathVariable Long id,
            @Valid @RequestBody UtenteRequest request
    ) {
        UtenteResponse utente = service.updateUtente(id, request);
        return utente == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(utente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable Long id) {
        service.deleteUtente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UtenteResponse> getUtenteByUsername(@PathVariable String username) {
        UtenteResponse utente = service.getUtenteByUsername(username);
        return utente == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(utente);
    }
}
