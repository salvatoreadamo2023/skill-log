package com.salvatore.skilllog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProgettoRequest {

    @NotBlank(message = "Il nome del progetto e' obbligatorio")
    @Size(max = 150, message = "Il nome non puo' superare 150 caratteri")
    private String nome;

    private String descrizione;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String stato;
}
