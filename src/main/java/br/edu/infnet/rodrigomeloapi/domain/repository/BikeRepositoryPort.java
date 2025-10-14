package br.edu.infnet.rodrigomeloapi.domain.repository;

import br.edu.infnet.rodrigomeloapi.domain.model.Bike;
import java.util.List;
import java.util.Optional;

public interface BikeRepositoryPort {
    Bike save(Bike bicicleta);           
    Optional<Bike> findById(Long id);
    List<Bike> findAll();
    void deleteById(Long id);
    
    List<Bike> findByOwnerId(Long ownerId);
    List<Bike> findByTypeIgnoreCase(String type);
}