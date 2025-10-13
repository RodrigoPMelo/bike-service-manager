package br.edu.infnet.rodrigomeloapi.api.controller;

import br.edu.infnet.rodrigomeloapi.application.service.BikeService;
import br.edu.infnet.rodrigomeloapi.domain.model.Bike;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bikes")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService service;

    @GetMapping
    public ResponseEntity<List<Bike>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bike> create(@RequestBody Bike body) {
        body.setId(null);
        Bike created = service.save(body);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created); // 201 + Location
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bike> update(@PathVariable Long id, @RequestBody Bike body) {
        var existing = service.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build(); // 404
        body.setId(id);
        return ResponseEntity.ok(service.save(body)); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var existing = service.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build(); // 404
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
