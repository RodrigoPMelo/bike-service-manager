package br.edu.infnet.rodrigomeloapi.api.controller;

import br.edu.infnet.rodrigomeloapi.application.service.ClientService;
import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    // --- READ ---
    @GetMapping
    public List<Client> listAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

    @GetMapping("/by-cpf/{cpf}")
    public Client getByCpf(@PathVariable String cpf) {
        return service.findByCpf(cpf).orElse(null);
    }

    @GetMapping("/search")
    public List<Client> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }

    // --- CREATE ---
    @PostMapping
    public Client create(@RequestBody Client body) {
        body.setId(null);
        return service.save(body);
    }

    // --- UPDATE (full) ---
    @PutMapping("/{id}")
    public Client update(@PathVariable Long id, @RequestBody Client body) {
        body.setId(id);
        return service.save(body);
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
