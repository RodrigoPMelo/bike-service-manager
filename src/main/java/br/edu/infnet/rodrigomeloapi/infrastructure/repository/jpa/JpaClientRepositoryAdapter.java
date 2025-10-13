package br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa;

import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import br.edu.infnet.rodrigomeloapi.domain.repository.ClientRepositoryPort;
import br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa.springdata.JpaClientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@org.springframework.context.annotation.Profile("jpa")
public class JpaClientRepositoryAdapter implements ClientRepositoryPort {

    private final JpaClientRepository repo;

    public JpaClientRepositoryAdapter(JpaClientRepository repo) {
        this.repo = repo;
    }

    @Override
    public Client save(Client client) {
        return repo.save(client);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Client> findByCpf(String cpf) {
        return repo.findByCpf(cpf);
    }

    @Override
    public List<Client> findByNameContainingIgnoreCase(String namePart) {
        return repo.findByNameContainingIgnoreCase(namePart);
    }
}
