package com.salvatore.skilllog.service;

import com.salvatore.skilllog.model.Progetto;
import com.salvatore.skilllog.repository.ProgettoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProgettoService {

    private final ProgettoRepository repository;

    public ProgettoService(ProgettoRepository repository) {
        this.repository = repository;
    }

    public List<Progetto> getAllProgetti() {
        return repository.findAll();
    }

    public Progetto getProgettoById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Progetto saveProgetto(Progetto progetto) {
        return repository.save(progetto);
    }

    public void deleteProgetto(Long id) {
        repository.deleteById(id);
    }

    public List<Progetto> getProgettiByStato(String stato) {
        return repository.findByStatoIgnoreCase(stato);
    }
}
