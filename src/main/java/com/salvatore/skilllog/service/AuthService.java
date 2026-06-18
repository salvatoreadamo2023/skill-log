package com.salvatore.skilllog.service;

import com.salvatore.skilllog.dto.AuthResponse;
import com.salvatore.skilllog.dto.LoginRequest;
import com.salvatore.skilllog.dto.RegisterRequest;
import com.salvatore.skilllog.exception.UsernameAlreadyExistsException;
import com.salvatore.skilllog.model.Utente;
import com.salvatore.skilllog.repository.UtenteRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UtenteRepository utenteRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            UtenteRepository utenteRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.utenteRepository = utenteRepository;
    }

    public AuthResponse register(RegisterRequest request) {
        if (utenteRepository.findByUsername(request.getUsername()) != null) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        Utente utente = Utente.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .ruolo("USER")
                .enabled(true)
                .build();

        utenteRepository.save(utente);

        UserDetails userDetails = userDetailsService.loadUserByUsername(utente.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token, "Bearer", utente.getUsername(), utente.getRuolo());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        Utente utente = utenteRepository.findByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, "Bearer", utente.getUsername(), utente.getRuolo());
    }
}
