package br.edu.infnet.rodrigomeloapi.api.controller;

import br.edu.infnet.rodrigomeloapi.application.service.BikeService;
import br.edu.infnet.rodrigomeloapi.application.service.ClientService;
import br.edu.infnet.rodrigomeloapi.domain.model.Bike;
import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;
    private final BikeService bikeService; 

    // READ
    @GetMapping
    public ResponseEntity<List<Client>> listAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-cpf/{cpf}")
    public ResponseEntity<Client> getByCpf(@PathVariable String cpf) {
        return service.findByCpf(cpf).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchByName(name));
    }
    @GetMapping("/{id}/bikes")
    public ResponseEntity<List<Bike>> listBikesOfClient(@PathVariable Long id) {
        var exists = service.findById(id).isPresent();
        if (!exists) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bikeService.findByOwner(id));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody Client body) {
        body.setId(null);
        Client created = service.save(body);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created); // 201
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @Valid @RequestBody Client body) {
        var existing = service.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        body.setId(id);
        return ResponseEntity.ok(service.save(body)); // 200
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var existing = service.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
    
}
