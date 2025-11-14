package com.salvatore.skilllog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salvatore.skilllog.model.Progetto;
import com.salvatore.skilllog.model.Skill;
import com.salvatore.skilllog.repository.ProgettoRepository;
import com.salvatore.skilllog.repository.SkillRepository;
import com.salvatore.skilllog.repository.UtenteRepository;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final UtenteRepository utenteRepository;
    private final SkillRepository skillRepository;
    private final ProgettoRepository progettoRepository;

    @Autowired
    public StatsController(UtenteRepository utenteRepository,
                           SkillRepository skillRepository,
                           ProgettoRepository progettoRepository) {
        this.utenteRepository = utenteRepository;
        this.skillRepository = skillRepository;
        this.progettoRepository = progettoRepository;
    }

    @GetMapping
    public Map<String, Object> getGeneralStats() {
        Map<String, Object> stats = new HashMap<>();

        // Utenti
        long totaleUtenti = utenteRepository.count();

        // Skill
        List<Skill> skills = skillRepository.findAll();
        long totaleSkill = skills.size();
        int totaleOreSkill = skills.stream().mapToInt(Skill::getOreStudio).sum();
        double mediaOreSkill = skills.stream().mapToInt(Skill::getOreStudio).average().orElse(0);

        // Progetti
        List<Progetto> progetti = progettoRepository.findAll();
        long totaleProgetti = progetti.size();
        long progettiCompletati = progetti.stream()
                                          .filter(p -> "COMPLETATO".equalsIgnoreCase(p.getStato()))
                                          .count();

        // Inserisco nel JSON
        stats.put("totaleUtenti", totaleUtenti);
        stats.put("totaleSkill", totaleSkill);
        stats.put("totaleOreSkill", totaleOreSkill);
        stats.put("mediaOreSkill", mediaOreSkill);
        stats.put("totaleProgetti", totaleProgetti);
        stats.put("progettiCompletati", progettiCompletati);

        return stats;
    }
}
