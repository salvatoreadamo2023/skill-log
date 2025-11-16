package com.salvatore.skilllog.repository;

import com.salvatore.skilllog.model.Progetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository // indica che è un componente Spring
public interface ProgettoRepository extends JpaRepository<Progetto, Long> {

    // Query personalizzata: trova progetti per stato
    List<Progetto> findByStato(String stato);

    // Query personalizzata: trova progetti attivi
    List<Progetto> findByStatoIgnoreCase(String stato);

    // Query personalizzata: trova progetti con nome contenente una parola
    List<Progetto> findByNomeContainingIgnoreCase(String keyword);
}
