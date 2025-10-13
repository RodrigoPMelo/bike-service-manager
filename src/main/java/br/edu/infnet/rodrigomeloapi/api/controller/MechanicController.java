package br.edu.infnet.rodrigomeloapi.api.controller;

import br.edu.infnet.rodrigomeloapi.application.service.MechanicService;
import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mechanics")
@RequiredArgsConstructor
public class MechanicController {

    private final MechanicService service;

    // --- READ ---
    @GetMapping
    public List<Mechanic> listAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mechanic getById(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

    @GetMapping("/active")
    public List<Mechanic> listByActive(@RequestParam(defaultValue = "true") boolean value) {
        return service.listByActive(value);
    }

    @GetMapping("/search")
    public List<Mechanic> searchBySpecialty(@RequestParam String specialtyPrefix) {
        return service.findBySpecialtyPrefix(specialtyPrefix);
    }

    // --- CREATE ---
    @PostMapping
    public Mechanic create(@RequestBody Mechanic body) {
        body.setId(null);
        return service.save(body);
    }

    // --- UPDATE (full) ---
    @PutMapping("/{id}")
    public Mechanic update(@PathVariable Long id, @RequestBody Mechanic body) {
        body.setId(id);
        return service.save(body);
    }

    // --- PATCH-like extra (inactivate) ---
    @PatchMapping("/{id}/inactivate")
    public Mechanic inactivate(@PathVariable Long id) {
        return service.inactivate(id).orElse(null);
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
