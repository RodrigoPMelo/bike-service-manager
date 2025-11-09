package br.edu.infnet.rodrigomeloapi.api.controller;

import br.edu.infnet.rodrigomeloapi.application.service.ServiceOrderService;
import br.edu.infnet.rodrigomeloapi.domain.model.ServiceOrder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/service-orders")
@RequiredArgsConstructor
public class ServiceOrderController {

    private final ServiceOrderService service;

    @GetMapping
    public ResponseEntity<List<ServiceOrder>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrder> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Query endpoints for Feature 4
    @GetMapping("/by-client-name")
    public ResponseEntity<List<ServiceOrder>> getByClientName(@RequestParam String name) {
        return ResponseEntity.ok(service.findByClientName(name));
    }

    @GetMapping("/by-mechanic-specialty")
    public ResponseEntity<List<ServiceOrder>> getByMechanicSpecialty(@RequestParam String specialty) {
        return ResponseEntity.ok(service.findByMechanicSpecialty(specialty));
    }

    @PostMapping
    public ResponseEntity<ServiceOrder> create(
            @Valid @RequestBody ServiceOrder body,
            @RequestParam Long clientId,
            @RequestParam Long mechanicId) {
        
        ServiceOrder created = service.saveNew(body, clientId, mechanicId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrder> update(@PathVariable Long id, @Valid @RequestBody ServiceOrder body) {
        // Ensures the ID in the body matches the URL and client/mechanic are not lost
        return service.findById(id).map(existing -> {
            body.setId(id);
            body.setClient(existing.getClient());
            body.setMechanic(existing.getMechanic());
            return ResponseEntity.ok(service.save(body));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}