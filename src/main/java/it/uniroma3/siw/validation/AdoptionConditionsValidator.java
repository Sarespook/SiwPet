package it.uniroma3.siw.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.AdoptionConditions;


@Component
public class AdoptionConditionsValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return AdoptionConditions.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		AdoptionConditions conditions =(AdoptionConditions)o;
		if (conditions.getMqs()<=0) {
			errors.reject("conditions.mqs");
		}
	}

}
