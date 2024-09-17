package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.AdoptionRequest;

public interface AdoptionRequestRepository extends CrudRepository<AdoptionRequest, Long> {
	
	public Optional<AdoptionRequest> findById(Long id);

	public Iterable<AdoptionRequest> findAllByAnimalId(Long animalId);
	
	public boolean existsByAnimalId(Long animalId);
	
	public boolean existsByAdopterIdAndAnimalId(Long adopterId,Long animalId);

	@Query(value="SELECT EXISTS( "
	        + "SELECT 1 "
	        + "FROM adoption_request a "
	        + "WHERE a.animal_id = :animalId "
	        + "AND a.accepted = true)", nativeQuery=true)
	public boolean existsAcceptedRequestByAnimalId(@Param("animalId") Long id);
	
	
}
