package br.edu.infnet.rodrigomeloapi.domain.repository;

import br.edu.infnet.rodrigomeloapi.domain.model.ServiceOrder;

import java.util.List;
import java.util.Optional;

public interface ServiceOrderRepositoryPort {
    ServiceOrder save(ServiceOrder serviceOrder);
    Optional<ServiceOrder> findById(Long id);
    List<ServiceOrder> findAll();
    void deleteById(Long id);

    // Query methods for Feature 4
    List<ServiceOrder> findByClientNameContainingIgnoreCase(String name);
    List<ServiceOrder> findByMechanicSpecialtyStartingWithIgnoreCase(String specialty);
}