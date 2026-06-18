package com.salvatore.skilllog.service;

import com.salvatore.skilllog.dto.SkillRequest;
import com.salvatore.skilllog.dto.SkillResponse;
import com.salvatore.skilllog.exception.SkillNotFoundException;
import com.salvatore.skilllog.mapper.SkillMapper;
import com.salvatore.skilllog.model.Skill;
import com.salvatore.skilllog.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(SkillMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SkillResponse createSkill(SkillRequest request) {
        Skill skill = SkillMapper.toEntity(request);
        return SkillMapper.toResponse(skillRepository.save(skill));
    }

    public SkillResponse updateSkill(Long id, SkillRequest updatedSkill) {
        return skillRepository.findById(id)
                .map(existing -> {
                    existing.setNome(updatedSkill.getNome());
                    existing.setLivello(updatedSkill.getLivello());
                    existing.setOreStudio(updatedSkill.getOreStudio());
                    return SkillMapper.toResponse(skillRepository.save(existing));
                })
                .orElseThrow(() -> new SkillNotFoundException(id));
    }

    public Optional<SkillResponse> getSkillById(Long id) {
        return skillRepository.findById(id).map(SkillMapper::toResponse);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

    public List<SkillResponse> searchByNome(String nome) {
        return skillRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(SkillMapper::toResponse)
                .collect(Collectors.toList());
    }
}
