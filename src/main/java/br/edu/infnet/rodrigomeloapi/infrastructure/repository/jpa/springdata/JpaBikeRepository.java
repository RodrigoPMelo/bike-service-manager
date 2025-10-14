package br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa.springdata;

import br.edu.infnet.rodrigomeloapi.domain.model.Bike;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBikeRepository extends JpaRepository<Bike, Long> { 
	List<Bike> findByOwner_Id(Long ownerId);
	List<Bike> findByTypeIgnoreCase(String type);
}
