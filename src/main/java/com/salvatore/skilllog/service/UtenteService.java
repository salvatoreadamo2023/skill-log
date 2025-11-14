package com.salvatore.skilllog.service;

import com.salvatore.skilllog.model.Utente;
import com.salvatore.skilllog.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    private final UtenteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Utente> getAllUtenti() {
        return repository.findAll();
    }

    public Utente getUtenteById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Utente saveUtente(Utente utente) {
        // Cripta la password se non è già bcryptata
        if (utente.getPassword() != null && !utente.getPassword().startsWith("$2a$")) {
            utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        }
        return repository.save(utente);
    }

    public void deleteUtente(Long id) {
        repository.deleteById(id);
    }

    public Utente getUtenteByUsername(String username) {
        return repository.findByUsername(username);
    }
}
