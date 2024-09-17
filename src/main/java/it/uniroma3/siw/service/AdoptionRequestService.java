package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.AdoptionRequest;
import it.uniroma3.siw.repository.AdoptionRequestRepository;

@Service
public class AdoptionRequestService {

	@Autowired
	private AdoptionRequestRepository adoptionReqRepository;

	public AdoptionRequest findById(Long id) {
		return adoptionReqRepository.findById(id).get();
	}

	public Iterable<AdoptionRequest> findAllByAnimalId(Long animalId) {
		return adoptionReqRepository.findAllByAnimalId(animalId);
	}
	
	public AdoptionRequest save(AdoptionRequest request) {
		return this.adoptionReqRepository.save(request);
	}
	
	public boolean existsByAdopterIdAndAnimalId(Long adopterId,Long animalId) {
		return this.adoptionReqRepository.existsByAdopterIdAndAnimalId(adopterId,animalId);
	}
	
	
	
	public void deleteById(Long id) {
		this.adoptionReqRepository.deleteById(id);
	}
	
	public void deleteAll(Iterable<AdoptionRequest> requests) {
		this.adoptionReqRepository.deleteAll(requests);
	}

	public boolean existsAcceptedRequestByAnimalId(Long animalId) {
		return this.adoptionReqRepository.existsAcceptedRequestByAnimalId(animalId);
	}

}
