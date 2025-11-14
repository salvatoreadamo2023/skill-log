package com.salvatore.skilllog.controller;

import com.salvatore.skilllog.model.Utente;
import com.salvatore.skilllog.service.UtenteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // indica che è un controller REST
@RequestMapping("/api/utenti") // base path per gli endpoint
public class UtenteController {

    private final UtenteService service;

    // Costruttore con injection del service
    public UtenteController(UtenteService service) {
        this.service = service;
    }

    // GET: lista di tutti gli utenti
    @GetMapping
    public List<Utente> getAllUtenti() {
        return service.getAllUtenti();
    }

    // GET: singolo utente per ID
    @GetMapping("/{id}")
    public Utente getUtenteById(@PathVariable Long id) {
        return service.getUtenteById(id);
    }

    // POST: crea un nuovo utente
    @PostMapping
    public Utente createUtente(@RequestBody Utente utente) {
        return service.saveUtente(utente);
    }

    // PUT: aggiorna un utente esistente
    @PutMapping("/{id}")
    public Utente updateUtente(@PathVariable Long id, @RequestBody Utente utente) {
        utente.setId(id);
        return service.saveUtente(utente);
    }

    // DELETE: elimina un utente
    @DeleteMapping("/{id}")
    public void deleteUtente(@PathVariable Long id) {
        service.deleteUtente(id);
    }

    // GET: trova utente per username
    @GetMapping("/username/{username}")
    public Utente getUtenteByUsername(@PathVariable String username) {
        return service.getUtenteByUsername(username);
    }
}
