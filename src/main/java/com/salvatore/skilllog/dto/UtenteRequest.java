package com.salvatore.skilllog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtenteRequest {

    @NotBlank(message = "Username obbligatorio")
    @Size(min = 3, max = 100, message = "Username deve avere tra 3 e 100 caratteri")
    private String username;

    @Size(min = 8, max = 100, message = "Password deve avere almeno 8 caratteri")
    private String password;

    @NotBlank(message = "Ruolo obbligatorio")
    private String ruolo;

    private boolean enabled = true;
}
