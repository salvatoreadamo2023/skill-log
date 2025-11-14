package com.salvatore.skilllog.service;

import com.salvatore.skilllog.exception.SkillNotFoundException;
import com.salvatore.skilllog.model.Skill;
import com.salvatore.skilllog.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }



    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill updatedSkill) {
        return skillRepository.findById(id)
                .map(existing -> {
                    existing.setNome(updatedSkill.getNome());
                    existing.setLivello(updatedSkill.getLivello());
                    existing.setOreStudio(updatedSkill.getOreStudio());
                    return skillRepository.save(existing);
                })
                .orElseThrow(() -> new SkillNotFoundException(id));
    }

    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
    public List<Skill> searchByNome(String nome) {
        return skillRepository.findByNomeContainingIgnoreCase(nome);
    }

}
