package com.salvatore.skilllog.service;

import com.salvatore.skilllog.dto.ProgettoRequest;
import com.salvatore.skilllog.dto.ProgettoResponse;
import com.salvatore.skilllog.mapper.ProgettoMapper;
import com.salvatore.skilllog.model.Progetto;
import com.salvatore.skilllog.repository.ProgettoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgettoService {

    private final ProgettoRepository repository;

    public ProgettoService(ProgettoRepository repository) {
        this.repository = repository;
    }

    public List<ProgettoResponse> getAllProgetti() {
        return repository.findAll().stream()
                .map(ProgettoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProgettoResponse getProgettoById(Long id) {
        return repository.findById(id)
                .map(ProgettoMapper::toResponse)
                .orElse(null);
    }

    public ProgettoResponse createProgetto(ProgettoRequest request) {
        Progetto progetto = ProgettoMapper.toEntity(request);
        return ProgettoMapper.toResponse(repository.save(progetto));
    }

    public ProgettoResponse updateProgetto(Long id, ProgettoRequest request) {
        Progetto progetto = repository.findById(id).orElse(null);
        if (progetto == null) {
            return null;
        }

        progetto.setNome(request.getNome());
        progetto.setDescrizione(request.getDescrizione());
        progetto.setDataInizio(request.getDataInizio());
        progetto.setDataFine(request.getDataFine());
        progetto.setStato(request.getStato());

        return ProgettoMapper.toResponse(repository.save(progetto));
    }

    public void deleteProgetto(Long id) {
        repository.deleteById(id);
    }

    public List<ProgettoResponse> getProgettiByStato(String stato) {
        return repository.findByStatoIgnoreCase(stato).stream()
                .map(ProgettoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
