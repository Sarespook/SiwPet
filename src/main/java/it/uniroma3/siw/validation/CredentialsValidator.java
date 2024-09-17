package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator{

	
	@Autowired
	private CredentialsService ceredentialsService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Credentials credentials =(Credentials) target;
		if (credentials.getUsername()!=null && this.ceredentialsService.existsByUsername(credentials.getUsername())) {
			errors.reject("credentials.duplicate");
		}
	}

}
