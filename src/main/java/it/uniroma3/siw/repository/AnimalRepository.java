package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Animal;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

	boolean existsByName(String name);

	boolean existsBySpecies(String race);

}
