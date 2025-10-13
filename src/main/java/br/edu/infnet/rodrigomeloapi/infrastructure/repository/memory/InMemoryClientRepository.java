package br.edu.infnet.rodrigomeloapi.infrastructure.repository.memory;

import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import br.edu.infnet.rodrigomeloapi.domain.repository.ClientRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@org.springframework.context.annotation.Profile("mem")
public class InMemoryClientRepository implements ClientRepositoryPort {

    private final Map<Long, Client> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0L);

    @Override
    public Client save(Client client) {
        if (client.getId() == null) {
            client.setId(seq.incrementAndGet());
        }
        // shallow copy to avoid leaking internal references
        Client copy = cloneOf(client);
        store.put(copy.getId(), copy);
        return cloneOf(copy);
    }

    @Override
    public Optional<Client> findById(Long id) {
        Client found = store.get(id);
        return Optional.ofNullable(found == null ? null : cloneOf(found));
    }

    @Override
    public List<Client> findAll() {
        return store.values().stream()
                .map(this::cloneOf)
                .sorted(Comparator.comparing(Client::getId))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public Optional<Client> findByCpf(String cpf) {
        if (cpf == null) return Optional.empty();
        String target = cpf.trim();
        return store.values().stream()
                .filter(c -> c.getCpf() != null && c.getCpf().equals(target))
                .findFirst()
                .map(this::cloneOf);
    }

    @Override
    public List<Client> findByNameContainingIgnoreCase(String namePart) {
        if (namePart == null || namePart.isBlank()) return List.of();
        String needle = namePart.toLowerCase();
        return store.values().stream()
                .filter(c -> c.getName() != null && c.getName().toLowerCase().contains(needle))
                .map(this::cloneOf)
                .sorted(Comparator.comparing(Client::getId))
                .toList();
    }

    private Client cloneOf(Client c) {
        if (c == null) return null;
        Client copy = new Client();
        // Person fields (super)
        copy.setName(c.getName());
        copy.setEmail(c.getEmail());
        copy.setCpf(c.getCpf());
        copy.setPhone(c.getPhone());
        // Client fields
        copy.setId(c.getId());
        copy.setLoyaltyCode(c.getLoyaltyCode());
        copy.setSignupAt(c.getSignupAt());
        // shallow copy of Address (value object)
        copy.setAddress(c.getAddress() == null ? null : c.getAddress());
        return copy;
    }
}
