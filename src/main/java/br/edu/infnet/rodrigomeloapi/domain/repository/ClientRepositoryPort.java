package br.edu.infnet.rodrigomeloapi.domain.repository;

import br.edu.infnet.rodrigomeloapi.domain.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryPort {
    Client save(Client client);
    Optional<Client> findById(Long id);
    List<Client> findAll();
    void deleteById(Long id);

    // extra methods useful for Feature 2 demos
    Optional<Client> findByCpf(String cpf);
    List<Client> findByNameContainingIgnoreCase(String namePart);
}
