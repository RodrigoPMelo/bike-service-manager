package br.edu.infnet.rodrigomeloapi.infrastructure.repository.memory;

import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;
import br.edu.infnet.rodrigomeloapi.domain.repository.MechanicRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@org.springframework.context.annotation.Profile("mem")
public class InMemoryMechanicRepository implements MechanicRepositoryPort {

    private final Map<Long, Mechanic> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0L);

    @Override
    public Mechanic save(Mechanic mechanic) {
        if (mechanic.getId() == null) {
            mechanic.setId(seq.incrementAndGet());
        }
        Mechanic copy = cloneOf(mechanic);
        store.put(copy.getId(), copy);
        return cloneOf(copy);
    }

    @Override
    public Optional<Mechanic> findById(Long id) {
        Mechanic found = store.get(id);
        return Optional.ofNullable(found == null ? null : cloneOf(found));
    }

    @Override
    public List<Mechanic> findAll() {
        return store.values().stream()
                .map(this::cloneOf)
                .sorted(Comparator.comparing(Mechanic::getId))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public List<Mechanic> findBySpecialtyStartingWithIgnoreCase(String prefix) {
        if (prefix == null) return List.of();
        String start = prefix.toLowerCase();
        return store.values().stream()
                .filter(m -> m.getSpecialty() != null && m.getSpecialty().toLowerCase().startsWith(start))
                .map(this::cloneOf)
                .sorted(Comparator.comparing(Mechanic::getId))
                .toList();
    }

    @Override
    public List<Mechanic> findByActive(boolean active) {
        return store.values().stream()
                .filter(m -> m.isActive() == active)
                .map(this::cloneOf)
                .sorted(Comparator.comparing(Mechanic::getId))
                .toList();
    }

    private Mechanic cloneOf(Mechanic m) {
        if (m == null) return null;
        Mechanic copy = new Mechanic();
        // Person fields
        copy.setName(m.getName());
        copy.setEmail(m.getEmail());
        copy.setCpf(m.getCpf());
        copy.setPhone(m.getPhone());
        // Mechanic fields
        copy.setId(m.getId());
        copy.setEmployeeNumber(m.getEmployeeNumber());
        copy.setSpecialty(m.getSpecialty());
        copy.setSalary(m.getSalary());
        copy.setActive(m.isActive());
        return copy;
    }
}
