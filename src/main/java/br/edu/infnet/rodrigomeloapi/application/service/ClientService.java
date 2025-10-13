package br.edu.infnet.rodrigomeloapi.application.service;

import br.edu.infnet.rodrigomeloapi.application.shared.CrudService;
import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import br.edu.infnet.rodrigomeloapi.domain.repository.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements CrudService<Client, Long> {

    private final ClientRepositoryPort repository;

    @Override
    public Client save(Client entity) {
        normalize(entity);
        // simple rule: set signupAt if not set
        if (entity.getSignupAt() == null) {
            entity.setSignupAt(LocalDateTime.now());
        }
        return repository.save(entity);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // convenience finders used in controllers later
    public Optional<Client> findByCpf(String cpf) {
        if (cpf == null) return Optional.empty();
        return repository.findByCpf(cpf.trim());
    }

    public List<Client> searchByName(String namePart) {
        return repository.findByNameContainingIgnoreCase(namePart);
    }

    private void normalize(Client c) {
        if (c == null) return;
        if (c.getName() != null) c.setName(c.getName().trim());
        if (c.getEmail() != null) c.setEmail(c.getEmail().trim().toLowerCase(Locale.ROOT));
        if (c.getCpf() != null) c.setCpf(c.getCpf().trim());
        if (c.getPhone() != null) c.setPhone(c.getPhone().trim());
        if (c.getLoyaltyCode() != null) c.setLoyaltyCode(c.getLoyaltyCode().trim());
        // Address left as-is for now (validation in Feature 4)
    }
}
