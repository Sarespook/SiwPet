package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user=(User)target;
		if (user.getName()!=null && user.getSurname()!=null && user.getEmail()!=null 
				&& this.userService.existsByNameAndSurnameAndEmail(user.getName(),user.getSurname(),user.getEmail())) {
			errors.reject("user.duplicate");
		}
	}

}
