package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Animal;
import it.uniroma3.siw.service.AnimalService;



@Component
public class AnimalValidator implements Validator {

		@Autowired
		private AnimalService animalService;

		@Override
		public boolean supports(Class<?> clazz) {
			return Animal.class.equals(clazz);
		}

		@Override
		public void validate(Object o, Errors errors) {
			Animal animal =(Animal)o;
			if (animal.getName()!=null && this.animalService.existsByName(animal.getName()) 
					&& this.animalService.existsBySpecies(animal.getSpecies())) {
				errors.reject("animal.duplicate");
			}
		}

}
