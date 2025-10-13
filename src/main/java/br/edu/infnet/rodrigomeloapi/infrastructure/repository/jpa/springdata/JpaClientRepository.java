package br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa.springdata;

import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCpf(String cpf);
    List<Client> findByNameContainingIgnoreCase(String namePart);
}
