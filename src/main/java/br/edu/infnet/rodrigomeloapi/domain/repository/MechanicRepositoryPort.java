package br.edu.infnet.rodrigomeloapi.domain.repository;

import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;

import java.util.List;
import java.util.Optional;

public interface MechanicRepositoryPort {
    Mechanic save(Mechanic mechanic);
    Optional<Mechanic> findById(Long id);
    List<Mechanic> findAll();
    void deleteById(Long id);

    // extra methods useful for Feature 2 demos
    List<Mechanic> findBySpecialtyStartingWithIgnoreCase(String prefix);
    List<Mechanic> findByActive(boolean active);
}
