package br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa;

import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;
import br.edu.infnet.rodrigomeloapi.domain.repository.MechanicRepositoryPort;
import br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa.springdata.JpaMechanicRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@org.springframework.context.annotation.Profile("jpa")
public class JpaMechanicRepositoryAdapter implements MechanicRepositoryPort {

    private final JpaMechanicRepository repo;

    public JpaMechanicRepositoryAdapter(JpaMechanicRepository repo) {
        this.repo = repo;
    }

    @Override
    public Mechanic save(Mechanic mechanic) {
        return repo.save(mechanic);
    }

    @Override
    public Optional<Mechanic> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<Mechanic> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Mechanic> findBySpecialtyStartingWithIgnoreCase(String prefix) {
        return repo.findBySpecialtyStartingWithIgnoreCase(prefix);
    }

    @Override
    public List<Mechanic> findByActive(boolean active) {
        return repo.findByActive(active);
    }
}
