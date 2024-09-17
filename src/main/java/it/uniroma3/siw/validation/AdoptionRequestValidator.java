package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.AdoptionRequest;
import it.uniroma3.siw.service.AdoptionRequestService;


@Component
public class AdoptionRequestValidator implements Validator{
	
	
	 @Autowired
	    private AdoptionRequestService requestService;

	    @Override
	    public boolean supports(Class<?> clazz) {
	        return AdoptionRequest.class.equals(clazz);
	    }

	    @Override
	    public void validate(Object o, Errors errors) {
	        AdoptionRequest request = (AdoptionRequest) o;

	        // Validate that there is no duplicate request by the same adopter for the same animal
	        if (this.requestService.existsAcceptedRequestByAnimalId(request.getAnimal().getId())==true) {
	        	errors.reject("request.duplicate");
	        }
	    }

}
