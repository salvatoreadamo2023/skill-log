package com.salvatore.skilllog.service;

import com.salvatore.skilllog.dto.UtenteRequest;
import com.salvatore.skilllog.dto.UtenteResponse;
import com.salvatore.skilllog.exception.ResourceNotFoundException;
import com.salvatore.skilllog.model.Utente;
import com.salvatore.skilllog.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UtenteServiceTest {

    @Mock
    private UtenteRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UtenteService utenteService;

    @Test
    void getAllUtenti_shouldReturnResponsesWithoutPassword() {
        Utente utente = Utente.builder()
                .id(1L)
                .username("salvatore")
                .password("encoded-password")
                .ruolo("ADMIN")
                .enabled(true)
                .build();

        when(repository.findAll()).thenReturn(List.of(utente));

        List<UtenteResponse> result = utenteService.getAllUtenti();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getUsername()).isEqualTo("salvatore");
        assertThat(result.get(0).getRuolo()).isEqualTo("ADMIN");
        assertThat(result.get(0).isEnabled()).isTrue();
    }

    @Test
    void createUtente_shouldEncodePasswordAndReturnResponse() {
        UtenteRequest request = new UtenteRequest();
        request.setUsername("user");
        request.setPassword("Password123!");
        request.setRuolo("USER");
        request.setEnabled(true);

        Utente saved = Utente.builder()
                .id(2L)
                .username("user")
                .password("encoded-password")
                .ruolo("USER")
                .enabled(true)
                .build();

        when(passwordEncoder.encode("Password123!")).thenReturn("encoded-password");
        when(repository.save(any(Utente.class))).thenReturn(saved);

        UtenteResponse response = utenteService.createUtente(request);

        ArgumentCaptor<Utente> utenteCaptor = ArgumentCaptor.forClass(Utente.class);
        verify(repository).save(utenteCaptor.capture());

        assertThat(utenteCaptor.getValue().getPassword()).isEqualTo("encoded-password");
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getUsername()).isEqualTo("user");
        assertThat(response.getRuolo()).isEqualTo("USER");
    }

    @Test
    void getUtenteById_shouldThrowWhenUserNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> utenteService.getUtenteById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
