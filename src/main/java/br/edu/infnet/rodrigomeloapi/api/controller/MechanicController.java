package br.edu.infnet.rodrigomeloapi.api.controller;

import br.edu.infnet.rodrigomeloapi.application.service.MechanicService;
import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mechanics")
@RequiredArgsConstructor
public class MechanicController {

    private final MechanicService service;

    // READ
    @GetMapping
    public ResponseEntity<List<Mechanic>> listAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<Mechanic> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Mechanic>> listByActive(@RequestParam(defaultValue = "true") boolean value) {
        return ResponseEntity.ok(service.listByActive(value));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Mechanic>> searchBySpecialty(@RequestParam String specialtyPrefix) {
        return ResponseEntity.ok(service.findBySpecialtyPrefix(specialtyPrefix));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Mechanic> create(@Valid @RequestBody Mechanic body) {
        body.setId(null);
        Mechanic created = service.save(body);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created); // 201
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Mechanic> update(@PathVariable Long id,@Valid @RequestBody Mechanic body) {
        var existing = service.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        body.setId(id);
        return ResponseEntity.ok(service.save(body)); // 200
    }

    // PATCH-like
    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<Mechanic> inactivate(@PathVariable Long id) {
        return service.inactivate(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
