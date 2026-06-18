package com.salvatore.skilllog.controller;

import com.salvatore.skilllog.dto.ProgettoRequest;
import com.salvatore.skilllog.dto.ProgettoResponse;
import com.salvatore.skilllog.service.ProgettoService;
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
@RequestMapping("/api/progetti")
public class ProgettoController {

    private final ProgettoService service;

    public ProgettoController(ProgettoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProgettoResponse> getAllProgetti() {
        return service.getAllProgetti();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgettoResponse> getProgettoById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProgettoById(id));
    }

    @PostMapping
    public ResponseEntity<ProgettoResponse> createProgetto(@Valid @RequestBody ProgettoRequest request) {
        return ResponseEntity.ok(service.createProgetto(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgettoResponse> updateProgetto(
            @PathVariable Long id,
            @Valid @RequestBody ProgettoRequest request
    ) {
        return ResponseEntity.ok(service.updateProgetto(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgetto(@PathVariable Long id) {
        service.deleteProgetto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stato/{stato}")
    public List<ProgettoResponse> getProgettiByStato(@PathVariable String stato) {
        return service.getProgettiByStato(stato);
    }
}
