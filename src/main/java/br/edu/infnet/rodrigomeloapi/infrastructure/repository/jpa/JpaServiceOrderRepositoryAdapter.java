package br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa;

import br.edu.infnet.rodrigomeloapi.domain.model.ServiceOrder;
import br.edu.infnet.rodrigomeloapi.domain.repository.ServiceOrderRepositoryPort;
import br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa.springdata.JpaServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@org.springframework.context.annotation.Profile("jpa")
@RequiredArgsConstructor
public class JpaServiceOrderRepositoryAdapter implements ServiceOrderRepositoryPort {

    private final JpaServiceOrderRepository repo;

    @Override
    public ServiceOrder save(ServiceOrder serviceOrder) {
        return repo.save(serviceOrder);
    }

    @Override
    public Optional<ServiceOrder> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<ServiceOrder> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<ServiceOrder> findByClientNameContainingIgnoreCase(String name) {
        return repo.findByClientNameContainingIgnoreCase(name);
    }

    @Override
    public List<ServiceOrder> findByMechanicSpecialtyStartingWithIgnoreCase(String specialty) {
        return repo.findByMechanicSpecialtyStartingWithIgnoreCase(specialty);
    }
}