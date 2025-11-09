package br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa.springdata;

import br.edu.infnet.rodrigomeloapi.domain.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    
    // Query by relationship (Feature 4, Section 5)
    List<ServiceOrder> findByClientNameContainingIgnoreCase(String name);
    
    // Query by relationship (Feature 4, Section 5)
    List<ServiceOrder> findByMechanicSpecialtyStartingWithIgnoreCase(String specialty);

    // Query Method for the Loader (find by natural key to avoid duplicates)
    Optional<ServiceOrder> findByEntryDateAndClientCpf(java.time.LocalDateTime entryDate, String cpf);
}