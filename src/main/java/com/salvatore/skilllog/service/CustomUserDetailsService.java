package com.salvatore.skilllog.service;

import com.salvatore.skilllog.model.Utente;
import com.salvatore.skilllog.repository.UtenteRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository repository;

    public CustomUserDetailsService(UtenteRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = repository.findByUsername(username);
        if (utente == null) {
            throw new UsernameNotFoundException("Utente non trovato: " + username);
        }

        return User.builder()
                .username(utente.getUsername())
                .password(utente.getPassword()) // deve essere già bcryptata
                .roles(utente.getRuolo())       // es. "ADMIN" o "USER"
                .disabled(!utente.isEnabled())
                .build();
    }
}
