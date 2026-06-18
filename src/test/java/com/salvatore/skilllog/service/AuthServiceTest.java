package com.salvatore.skilllog.service;

import com.salvatore.skilllog.dto.AuthResponse;
import com.salvatore.skilllog.dto.LoginRequest;
import com.salvatore.skilllog.dto.RegisterRequest;
import com.salvatore.skilllog.exception.UsernameAlreadyExistsException;
import com.salvatore.skilllog.model.Utente;
import com.salvatore.skilllog.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UtenteRepository utenteRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldCreateEnabledUserWithUserRoleAndReturnToken() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("salvatore");
        request.setPassword("Password123!");

        UserDetails userDetails = User.withUsername("salvatore")
                .password("encoded-password")
                .roles("USER")
                .build();

        when(utenteRepository.findByUsername("salvatore")).thenReturn(null);
        when(passwordEncoder.encode("Password123!")).thenReturn("encoded-password");
        when(userDetailsService.loadUserByUsername("salvatore")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");

        AuthResponse response = authService.register(request);

        ArgumentCaptor<Utente> utenteCaptor = ArgumentCaptor.forClass(Utente.class);
        verify(utenteRepository).save(utenteCaptor.capture());
        Utente savedUtente = utenteCaptor.getValue();

        assertThat(savedUtente.getUsername()).isEqualTo("salvatore");
        assertThat(savedUtente.getPassword()).isEqualTo("encoded-password");
        assertThat(savedUtente.getRuolo()).isEqualTo("USER");
        assertThat(savedUtente.isEnabled()).isTrue();
        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getTokenType()).isEqualTo("Bearer");
        assertThat(response.getUsername()).isEqualTo("salvatore");
        assertThat(response.getRuolo()).isEqualTo("USER");
    }

    @Test
    void register_shouldThrowWhenUsernameAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("salvatore");
        request.setPassword("Password123!");

        when(utenteRepository.findByUsername("salvatore")).thenReturn(new Utente());

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(UsernameAlreadyExistsException.class)
                .hasMessageContaining("salvatore");
    }

    @Test
    void login_shouldAuthenticateAndReturnToken() {
        LoginRequest request = new LoginRequest();
        request.setUsername("salvatore");
        request.setPassword("Password123!");

        UserDetails userDetails = User.withUsername("salvatore")
                .password("encoded-password")
                .roles("ADMIN")
                .build();
        Utente utente = Utente.builder()
                .username("salvatore")
                .password("encoded-password")
                .ruolo("ADMIN")
                .enabled(true)
                .build();

        when(userDetailsService.loadUserByUsername("salvatore")).thenReturn(userDetails);
        when(utenteRepository.findByUsername("salvatore")).thenReturn(utente);
        when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getUsername()).isEqualTo("salvatore");
        assertThat(response.getRuolo()).isEqualTo("ADMIN");
    }
}
