package com.salvatore.skilllog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UtenteResponse {

    private Long id;
    private String username;
    private String ruolo;
    private boolean enabled;
}
