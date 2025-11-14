package com.salvatore.skilllog.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity // indica che questa classe è una tabella JPA
@Table(name = "utenti") // nome tabella nel DB
@Getter @Setter // Lombok: genera getter e setter
@NoArgsConstructor // costruttore vuoto (necessario per JPA)
@AllArgsConstructor // costruttore con tutti i campi
@Builder // pattern builder per creare oggetti fluenti
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // chiave primaria auto_increment
    private Long id;

    @Column(nullable=false, unique=true, length=100)
    private String username; // nome utente unico

    @Column(nullable=false)
    private String password; // password criptata (BCrypt)

    @Column(nullable=false, length=50)
    private String ruolo; // ADMIN, USER

    @Column(nullable=false)
    private boolean enabled; // se l’utente è attivo

    // Relazione molti-a-molti con Skill
    @ManyToMany
    @JoinTable(
        name = "utente_skill", // tabella ponte
        joinColumns = @JoinColumn(name = "utente_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Skill> skills = new HashSet<>();

    // Relazione molti-a-molti con Progetto
    @ManyToMany
    @JoinTable(
        name = "utente_progetto", // tabella ponte
        joinColumns = @JoinColumn(name = "utente_id"),
        inverseJoinColumns = @JoinColumn(name = "progetto_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Progetto> progetti = new HashSet<>();
}
