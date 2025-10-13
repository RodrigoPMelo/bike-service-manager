package br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa.springdata;

import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMechanicRepository extends JpaRepository<Mechanic, Long> {
    List<Mechanic> findBySpecialtyStartingWithIgnoreCase(String prefix);
    List<Mechanic> findByActive(boolean active);
}
