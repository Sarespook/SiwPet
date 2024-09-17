package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Adopter;
import it.uniroma3.siw.repository.AdopterRepository;

@Service
public class AdopterService {

	
	@Autowired
	private AdopterRepository adopterRepository;
	
	
	public Adopter save(Adopter adopter) {
		return this.adopterRepository.save(adopter);
	}


	public Adopter findById(Long id) {
		return this.adopterRepository.findById(id).get();
	}
	
	public boolean existsById(Long id) {
		return this.adopterRepository.existsById(id);
	}
}
