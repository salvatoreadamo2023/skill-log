package com.salvatore.skilllog.controller;

import com.salvatore.skilllog.model.Skill;
import com.salvatore.skilllog.service.SkillService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

 
    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
        System.out.println("SkillController inizializzato");
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        return skillService.getSkillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) {
        Skill created = skillService.createSkill(skill);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @Valid @RequestBody Skill skill) {
        Skill updated = skillService.updateSkill(id, skill);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public List<Skill> searchSkills(@RequestParam String nome) {
        return skillService.searchByNome(nome);
    }

}
