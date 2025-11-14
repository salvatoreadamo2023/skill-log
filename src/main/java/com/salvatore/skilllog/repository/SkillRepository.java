package com.salvatore.skilllog.repository;

import com.salvatore.skilllog.model.Skill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    // Esempio: List<Skill> findByLivello(String livello);
	List<Skill> findByNomeContainingIgnoreCase(String nome);
}


