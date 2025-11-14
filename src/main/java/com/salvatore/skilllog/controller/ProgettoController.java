package com.salvatore.skilllog.controller;

import com.salvatore.skilllog.model.Progetto;
import com.salvatore.skilllog.service.ProgettoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/progetti")
public class ProgettoController {

    private final ProgettoService service;

    public ProgettoController(ProgettoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Progetto> getAllProgetti() {
        return service.getAllProgetti();
    }

    @GetMapping("/{id}")
    public Progetto getProgettoById(@PathVariable Long id) {
        return service.getProgettoById(id);
    }

    @PostMapping
    public Progetto createProgetto(@RequestBody Progetto progetto) {
        return service.saveProgetto(progetto);
    }

    @PutMapping("/{id}")
    public Progetto updateProgetto(@PathVariable Long id, @RequestBody Progetto progetto) {
        progetto.setId(id);
        return service.saveProgetto(progetto);
    }

    @DeleteMapping("/{id}")
    public void deleteProgetto(@PathVariable Long id) {
        service.deleteProgetto(id);
    }

    @GetMapping("/stato/{stato}")
    public List<Progetto> getProgettiByStato(@PathVariable String stato) {
        return service.getProgettiByStato(stato);
    }
}
