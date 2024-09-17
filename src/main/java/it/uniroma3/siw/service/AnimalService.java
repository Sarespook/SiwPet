package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Animal;
import it.uniroma3.siw.repository.AnimalRepository;
import jakarta.validation.Valid;

@Service
public class AnimalService {
	
	@Autowired
	private AnimalRepository animalRepository;
	
	
	public Object findAll(){
		return this.animalRepository.findAll();
	}


	public Animal findById(Long id) {
		return this.animalRepository.findById(id).get();
	}


	public boolean existsByName(String name) {
		return this.animalRepository.existsByName(name);
	}
	
	public boolean existsById(Long id) {
		return this.animalRepository.existsById(id);
	}


	public boolean existsBySpecies(String race) {
		return this.animalRepository.existsBySpecies(race);
	}


	public void save(@Valid Animal animal) {
		this.animalRepository.save(animal);
	}


	public void deleteById(Long id) {
		this.animalRepository.deleteById(id);
	}

}
