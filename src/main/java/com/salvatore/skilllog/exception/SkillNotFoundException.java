package com.salvatore.skilllog.exception;

public class SkillNotFoundException extends ResourceNotFoundException {
    public SkillNotFoundException(Long id) {
        super("Skill non trovata con id: " + id);
    }
}
