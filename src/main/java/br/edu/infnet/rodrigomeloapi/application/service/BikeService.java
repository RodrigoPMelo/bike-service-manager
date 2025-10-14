package br.edu.infnet.rodrigomeloapi.application.service;

import br.edu.infnet.rodrigomeloapi.application.shared.CrudService;
import br.edu.infnet.rodrigomeloapi.domain.model.Bike;
import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import br.edu.infnet.rodrigomeloapi.domain.repository.BikeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BikeService implements CrudService<Bike, Long> {

    private final BikeRepositoryPort repository;

    @Override
    public Bike save(Bike entity) {
        if (entity.getModel() != null) entity.setModel(entity.getModel().trim());
        if (entity.getBrand() != null) entity.setBrand(entity.getBrand().trim());
        if (entity.getSerialNumber() != null) entity.setSerialNumber(entity.getSerialNumber().trim());
        if (entity.getType() != null) entity.setType(entity.getType().trim().toLowerCase());
        return repository.save(entity);
    }

    @Override
    public Optional<Bike> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Bike> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<Bike> findByOwner(Long ownerId) {
        return repository.findByOwnerId(ownerId);
    }
    public List<Bike> findByType(String type) {
        return repository.findByTypeIgnoreCase(type);
    }

    public Optional<Bike> assignOwner(Long bikeId, Client owner) {
        return repository.findById(bikeId).map(b -> {
            b.setOwner(owner);
            return repository.save(b);
        });
    }

}
