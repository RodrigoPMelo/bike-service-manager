package br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa;

import br.edu.infnet.rodrigomeloapi.domain.model.Bike;
import br.edu.infnet.rodrigomeloapi.domain.repository.BikeRepositoryPort;
import br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa.springdata.JpaBikeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@org.springframework.context.annotation.Profile("jpa")
public class JpaBikeRepositoryAdapter implements BikeRepositoryPort {

    private final JpaBikeRepository repo;

    public JpaBikeRepositoryAdapter(JpaBikeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Bike save(Bike bike) {
        return repo.save(bike);
    }

    @Override
    public Optional<Bike> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<Bike> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    
    @Override
    public List<Bike> findByOwnerId(Long ownerId) {
        return repo.findByOwner_Id(ownerId);
    }
    @Override
    public List<Bike> findByTypeIgnoreCase(String type) {
        return repo.findByTypeIgnoreCase(type);
    }
}
