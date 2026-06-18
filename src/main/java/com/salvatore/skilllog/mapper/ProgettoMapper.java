package com.salvatore.skilllog.mapper;

import com.salvatore.skilllog.dto.ProgettoRequest;
import com.salvatore.skilllog.dto.ProgettoResponse;
import com.salvatore.skilllog.model.Progetto;

public final class ProgettoMapper {

    private ProgettoMapper() {
    }

    public static Progetto toEntity(ProgettoRequest request) {
        return Progetto.builder()
                .nome(request.getNome())
                .descrizione(request.getDescrizione())
                .dataInizio(request.getDataInizio())
                .dataFine(request.getDataFine())
                .stato(request.getStato())
                .build();
    }

    public static ProgettoResponse toResponse(Progetto progetto) {
        return new ProgettoResponse(
                progetto.getId(),
                progetto.getNome(),
                progetto.getDescrizione(),
                progetto.getDataInizio(),
                progetto.getDataFine(),
                progetto.getStato()
        );
    }
}
