package com.salvatore.skilllog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
    @Column(nullable=false, length=50)
    private String nome;

    @NotBlank(message = "Il livello è obbligatorio")
    @Column(nullable=false, length=20)
    private String livello; // es: Base, Intermedio, Avanzato

    @Min(value = 1, message = "Le ore di studio devono essere almeno 1")
    @Column(nullable=false)
    private int oreStudio;

    @ManyToMany(mappedBy = "skills") 
    // relazione gestita dal lato Utente
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Utente> utenti = new HashSet<>();
}
