package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Adopter;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.validation.CredentialsValidator;
import it.uniroma3.siw.validation.UserValidator;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@Autowired
	private CredentialsService credentialsService;

    @GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "register.html";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "login.html";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "index";
		}
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("userDetails", userDetails);
		if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
			return "/user/index.html";
			}
		return "/admin/index.html";
	}
	
	
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
    		model.addAttribute("userDetails", userDetails);
            return "/user/index.html";
        }
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
    		model.addAttribute("userDetails", userDetails);
            return "/admin/index.html";
        }
    	model.addAttribute("userDetails", userDetails);
        return "index";
    }

	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {
		
		userValidator.validate(user,userBindingResult);
		credentialsValidator.validate(credentials,credentialsBindingResult);
		
		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
        	Adopter adopter=new Adopter();
        	adopter.setName(user.getName());
        	adopter.setSurname(user.getSurname());
        	
        	user.setAdopter(adopter);
        	    
            credentials.setUser(user);
            
            credentialsService.saveCredentials(credentials);   //idem sopra
            model.addAttribute("user", user);
          
            return "registrationSuccessful";
        }
        return "register";
    }
	
}
