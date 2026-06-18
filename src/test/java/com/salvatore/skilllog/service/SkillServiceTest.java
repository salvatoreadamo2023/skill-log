package com.salvatore.skilllog.service;

import com.salvatore.skilllog.dto.SkillRequest;
import com.salvatore.skilllog.dto.SkillResponse;
import com.salvatore.skilllog.exception.SkillNotFoundException;
import com.salvatore.skilllog.model.Skill;
import com.salvatore.skilllog.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    @Test
    void getAllSkills_shouldReturnSkillResponses() {
        Skill java = Skill.builder()
                .id(1L)
                .nome("Java")
                .livello("Intermedio")
                .oreStudio(40)
                .build();

        when(skillRepository.findAll()).thenReturn(List.of(java));

        List<SkillResponse> result = skillService.getAllSkills();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getNome()).isEqualTo("Java");
        assertThat(result.get(0).getLivello()).isEqualTo("Intermedio");
        assertThat(result.get(0).getOreStudio()).isEqualTo(40);
    }

    @Test
    void createSkill_shouldSaveAndReturnResponse() {
        SkillRequest request = new SkillRequest();
        request.setNome("Spring Boot");
        request.setLivello("Avanzato");
        request.setOreStudio(30);

        Skill savedSkill = Skill.builder()
                .id(10L)
                .nome("Spring Boot")
                .livello("Avanzato")
                .oreStudio(30)
                .build();

        when(skillRepository.save(org.mockito.ArgumentMatchers.any(Skill.class))).thenReturn(savedSkill);

        SkillResponse response = skillService.createSkill(request);

        ArgumentCaptor<Skill> skillCaptor = ArgumentCaptor.forClass(Skill.class);
        verify(skillRepository).save(skillCaptor.capture());
        Skill skillToSave = skillCaptor.getValue();

        assertThat(skillToSave.getNome()).isEqualTo("Spring Boot");
        assertThat(skillToSave.getLivello()).isEqualTo("Avanzato");
        assertThat(skillToSave.getOreStudio()).isEqualTo(30);
        assertThat(response.getId()).isEqualTo(10L);
    }

    @Test
    void updateSkill_shouldUpdateExistingSkill() {
        Skill existing = Skill.builder()
                .id(1L)
                .nome("Java")
                .livello("Base")
                .oreStudio(10)
                .build();

        SkillRequest request = new SkillRequest();
        request.setNome("Java");
        request.setLivello("Intermedio");
        request.setOreStudio(25);

        when(skillRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(skillRepository.save(existing)).thenReturn(existing);

        SkillResponse response = skillService.updateSkill(1L, request);

        assertThat(response.getLivello()).isEqualTo("Intermedio");
        assertThat(response.getOreStudio()).isEqualTo(25);
        verify(skillRepository).save(existing);
    }

    @Test
    void updateSkill_shouldThrowWhenSkillNotFound() {
        SkillRequest request = new SkillRequest();
        request.setNome("Java");
        request.setLivello("Intermedio");
        request.setOreStudio(25);

        when(skillRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> skillService.updateSkill(99L, request))
                .isInstanceOf(SkillNotFoundException.class)
                .hasMessageContaining("99");
    }
}
