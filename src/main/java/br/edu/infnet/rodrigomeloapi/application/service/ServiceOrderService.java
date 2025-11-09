package br.edu.infnet.rodrigomeloapi.application.service;

import br.edu.infnet.rodrigomeloapi.application.shared.CrudService;
import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;
import br.edu.infnet.rodrigomeloapi.domain.model.ServiceOrder;
import br.edu.infnet.rodrigomeloapi.domain.repository.ClientRepositoryPort;
import br.edu.infnet.rodrigomeloapi.domain.repository.MechanicRepositoryPort;
import br.edu.infnet.rodrigomeloapi.domain.repository.ServiceOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceOrderService implements CrudService<ServiceOrder, Long> {

    private final ServiceOrderRepositoryPort repository;
    private final ClientRepositoryPort clientRepository;
    private final MechanicRepositoryPort mechanicRepository;

    @Override
    public ServiceOrder save(ServiceOrder entity) {
        // Ensures part associations are correct
        if (entity.getPartsUsed() != null) {
            entity.getPartsUsed().forEach(part -> part.setServiceOrder(entity));
        }
        return repository.save(entity);
    }

    /**
     * Saves a new ServiceOrder by associating it with an existing Client and Mechanic.
     */
    public ServiceOrder saveNew(ServiceOrder entity, Long clientId, Long mechanicId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new java.util.NoSuchElementException("Client not found with ID: " + clientId));
        Mechanic mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new java.util.NoSuchElementException("Mechanic not found with ID: " + mechanicId));
        
        entity.setId(null); // Ensures it's a new creation
        entity.setClient(client);
        entity.setMechanic(mechanic);
        
        return save(entity);
    }

    @Override
    public Optional<ServiceOrder> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ServiceOrder> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!repository.findById(id).isPresent()) {
            throw new java.util.NoSuchElementException("ServiceOrder not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    // Query methods for Feature 4
    public List<ServiceOrder> findByClientName(String name) {
        return repository.findByClientNameContainingIgnoreCase(name);
    }

    public List<ServiceOrder> findByMechanicSpecialty(String specialty) {
        return repository.findByMechanicSpecialtyStartingWithIgnoreCase(specialty);
    }
}