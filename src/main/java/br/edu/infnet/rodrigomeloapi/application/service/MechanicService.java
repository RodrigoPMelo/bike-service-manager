package br.edu.infnet.rodrigomeloapi.application.service;

import br.edu.infnet.rodrigomeloapi.application.shared.CrudService;
import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;
import br.edu.infnet.rodrigomeloapi.domain.repository.MechanicRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MechanicService implements CrudService<Mechanic, Long> {

    private final MechanicRepositoryPort repository;

    @Override
    public Mechanic save(Mechanic entity) {
        normalize(entity);
        return repository.save(entity);
    }

    @Override
    public Optional<Mechanic> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Mechanic> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // extra use cases for Feature 2
    public List<Mechanic> findBySpecialtyPrefix(String prefix) {
        return repository.findBySpecialtyStartingWithIgnoreCase(prefix);
    }

    public List<Mechanic> listByActive(boolean active) {
        return repository.findByActive(active);
    }

    /** Partial update-style operation (PATCH-like for Feature 2 demos) */
    public Optional<Mechanic> inactivate(Long id) {
        Optional<Mechanic> opt = repository.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Mechanic m = opt.get();
        m.setActive(false);
        return Optional.of(repository.save(m));
    }

    private void normalize(Mechanic m) {
        if (m == null) return;
        if (m.getName() != null) m.setName(m.getName().trim());
        if (m.getEmail() != null) m.setEmail(m.getEmail().trim().toLowerCase(Locale.ROOT));
        if (m.getCpf() != null) m.setCpf(m.getCpf().trim());
        if (m.getPhone() != null) m.setPhone(m.getPhone().trim());
        if (m.getEmployeeNumber() != null) m.setEmployeeNumber(m.getEmployeeNumber().trim());
        if (m.getSpecialty() != null) m.setSpecialty(m.getSpecialty().trim());
        // salary/active remain as provided
    }
}
