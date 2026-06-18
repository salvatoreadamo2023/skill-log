package com.salvatore.skilllog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SkillResponse {

    private Long id;
    private String nome;
    private String livello;
    private int oreStudio;
}
