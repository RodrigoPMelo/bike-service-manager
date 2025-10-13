package br.edu.infnet.rodrigomeloapi.infrastructure.repository.memory;

import br.edu.infnet.rodrigomeloapi.domain.model.Bike;
import br.edu.infnet.rodrigomeloapi.domain.repository.BikeRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryBikeRepository implements BikeRepositoryPort {

    private final Map<Long, Bike> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0L);

    @Override
    public Bike save(Bike bike) {
        if (bike.getId() == null) {
            bike.setId(seq.incrementAndGet());
        }
        Bike copy = cloneOf(bike);
        store.put(copy.getId(), copy);
        return cloneOf(copy);
    }

    @Override
    public Optional<Bike> findById(Long id) {
        Bike found = store.get(id);
        return Optional.ofNullable(found == null ? null : cloneOf(found));
    }

    @Override
    public List<Bike> findAll() {
        return store.values().stream()
                .map(this::cloneOf)
                .sorted(Comparator.comparing(Bike::getId))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    private Bike cloneOf(Bike b) {
        if (b == null) return null;
        return Bike.builder()
                .id(b.getId())
                .model(b.getModel())
                .brand(b.getBrand())
                .year(b.getYear())
                .serialNumber(b.getSerialNumber())
                .type(b.getType())
                .build();
    }
}
