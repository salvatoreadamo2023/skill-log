package com.salvatore.skilllog.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity // indica che questa classe è una tabella JPA
@Table(name = "progetti") // nome tabella nel DB
@Getter @Setter // Lombok: genera getter e setter
@NoArgsConstructor // costruttore vuoto (necessario per JPA)
@AllArgsConstructor // costruttore con tutti i campi
@Builder // pattern builder per creare oggetti fluenti
public class Progetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // chiave primaria auto_increment
    private Long id;

    @Column(nullable=false, length=150)
    private String nome; // nome del progetto

    @Column(columnDefinition="TEXT")
    private String descrizione; // descrizione estesa

    @Column
    private LocalDate dataInizio; // data di inizio progetto

    @Column
    private LocalDate dataFine; // data di fine progetto

    @Column(length=50)
    private String stato; // ACTIVE, COMPLETED, ON_HOLD

    @ManyToMany(mappedBy = "progetti") 
    // relazione molti-a-molti gestita dal lato Utente
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Utente> utenti = new HashSet<>();
}
