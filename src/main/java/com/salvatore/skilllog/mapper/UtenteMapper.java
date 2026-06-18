package com.salvatore.skilllog.mapper;

import com.salvatore.skilllog.dto.UtenteRequest;
import com.salvatore.skilllog.dto.UtenteResponse;
import com.salvatore.skilllog.model.Utente;

public final class UtenteMapper {

    private UtenteMapper() {
    }

    public static Utente toEntity(UtenteRequest request) {
        return Utente.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .ruolo(request.getRuolo())
                .enabled(request.isEnabled())
                .build();
    }

    public static UtenteResponse toResponse(Utente utente) {
        return new UtenteResponse(
                utente.getId(),
                utente.getUsername(),
                utente.getRuolo(),
                utente.isEnabled()
        );
    }
}
