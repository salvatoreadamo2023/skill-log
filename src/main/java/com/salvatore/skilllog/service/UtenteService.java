package com.salvatore.skilllog.service;

import com.salvatore.skilllog.dto.UtenteRequest;
import com.salvatore.skilllog.dto.UtenteResponse;
import com.salvatore.skilllog.mapper.UtenteMapper;
import com.salvatore.skilllog.model.Utente;
import com.salvatore.skilllog.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtenteService {

    private final UtenteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UtenteResponse> getAllUtenti() {
        return repository.findAll().stream()
                .map(UtenteMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UtenteResponse getUtenteById(Long id) {
        return repository.findById(id)
                .map(UtenteMapper::toResponse)
                .orElse(null);
    }

    public UtenteResponse createUtente(UtenteRequest request) {
        Utente utente = UtenteMapper.toEntity(request);
        return UtenteMapper.toResponse(saveUtente(utente));
    }

    public UtenteResponse updateUtente(Long id, UtenteRequest request) {
        Utente utente = repository.findById(id).orElse(null);
        if (utente == null) {
            return null;
        }

        utente.setUsername(request.getUsername());
        utente.setRuolo(request.getRuolo());
        utente.setEnabled(request.isEnabled());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            utente.setPassword(request.getPassword());
        }

        return UtenteMapper.toResponse(saveUtente(utente));
    }

    public void deleteUtente(Long id) {
        repository.deleteById(id);
    }

    public UtenteResponse getUtenteByUsername(String username) {
        Utente utente = repository.findByUsername(username);
        return utente == null ? null : UtenteMapper.toResponse(utente);
    }

    private Utente saveUtente(Utente utente) {
        if (utente.getPassword() != null && !utente.getPassword().startsWith("$2a$")) {
            utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        }
        return repository.save(utente);
    }
}
