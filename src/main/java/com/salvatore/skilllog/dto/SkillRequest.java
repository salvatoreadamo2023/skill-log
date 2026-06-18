package com.salvatore.skilllog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillRequest {

    @NotBlank(message = "Il nome non puo' essere vuoto")
    @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
    private String nome;

    @NotBlank(message = "Il livello e' obbligatorio")
    private String livello;

    @Min(value = 1, message = "Le ore di studio devono essere almeno 1")
    private int oreStudio;
}
