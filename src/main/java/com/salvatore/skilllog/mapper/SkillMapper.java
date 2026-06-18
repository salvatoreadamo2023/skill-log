package com.salvatore.skilllog.mapper;

import com.salvatore.skilllog.dto.SkillRequest;
import com.salvatore.skilllog.dto.SkillResponse;
import com.salvatore.skilllog.model.Skill;

public final class SkillMapper {

    private SkillMapper() {
    }

    public static Skill toEntity(SkillRequest request) {
        return Skill.builder()
                .nome(request.getNome())
                .livello(request.getLivello())
                .oreStudio(request.getOreStudio())
                .build();
    }

    public static SkillResponse toResponse(Skill skill) {
        return new SkillResponse(
                skill.getId(),
                skill.getNome(),
                skill.getLivello(),
                skill.getOreStudio()
        );
    }
}
