package it.uniroma3.siw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Adopter;
import it.uniroma3.siw.model.AdoptionRequest;
import it.uniroma3.siw.model.Animal;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.AdopterService;
import it.uniroma3.siw.service.AdoptionRequestService;
import it.uniroma3.siw.service.AnimalService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.validation.AdoptionRequestValidator;
import jakarta.validation.Valid;

@Controller
public class AdoptionRequestController {
	
	
	@Value("${upload.path}")
    private String uploadPath;
	
	@Autowired
	private AdoptionRequestService requestService;
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private CredentialsService credService;

	@Autowired
	private AnimalService  animalService;
	
	@Autowired
	private AdoptionRequestValidator requestValidator;

	@Autowired
	private AdopterService adopterService;
	
	
	
	/*questo metodo ha utilità nel solo contesto della consultazione da parte dell'admin : consente di visionare , per un animale nella
	 * lista di adozione , tutte le richieste fatte a */
	@GetMapping("/admin/requests/{animalId}")
	private String getRequestsByAnimalToAccept(Model model,@PathVariable("animalId")Long animalId){
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("animal",this.animalService.findById(animalId));
		model.addAttribute("requests",this.requestService.findAllByAnimalId(animalId));
		return "/admin/requests.html";
	}
	

	 
	/*questo metodo ha utilità nel solo contesto della consultazione da parte dell'admin : consente di visonare la singola richiesta di adozione , nonche' di accettarla*/
	@GetMapping("/admin/request/{id}")
	private String getRequestByAnimalToAccept(Model model,@PathVariable("id")Long id) {
		model.addAttribute("userDetails", this.globalController.getUser());
		AdoptionRequest request=this.requestService.findById(id);
		model.addAttribute("request",request);
		model.addAttribute("animal",request.getAnimal());    //superflui
		model.addAttribute("adopter",request.getAdopter());  //superflui
		return "/admin/request.html";
	}
		
	
	
	@GetMapping("/admin/accept/{id}")  /*quando l'admin accetta una richiesta , tutte le altre relative allo stesso animale vengono automaticamnete cancellate*/
	private String acceptRequest(@PathVariable("id")Long id,Model model) {
		model.addAttribute("userDetails",this.globalController.getUser());
		AdoptionRequest request=this.requestService.findById(id);
		Animal animal = request.getAnimal();
		model.addAttribute("request",request);
		model.addAttribute("animal",request.getAnimal());
		model.addAttribute("adopter",request.getAdopter());
		
		/*ottieni un'istanzza della collezione/lista di richieste associate all'animale ,
		 *poi togli dalla lista l'unica richiesta che intendi accettare*/
		List <AdoptionRequest> otherRequests=(List<AdoptionRequest>) this.requestService.findAllByAnimalId(animal.getId());
		otherRequests.remove(request);  
		
		/*itera sulla lista così ottenuta e rimuovi le istanze delle richieste la dove sono presenti*/
		for(AdoptionRequest iter : otherRequests) {
			
			Adopter currentAdopter=iter.getAdopter();
			currentAdopter.getRequests().remove(iter);
			this.requestService.deleteById(iter.getId());
			this.adopterService.save(currentAdopter);
			
		}
		
		/*poni uguale a 'true' l'accettazione della richiesta originaria e salva lo stato corrente*/
		request.setAccepted(true);
		this.requestService.save(request);
;		return "redirect:/admin/requests/"+request.getAnimal().getId();
	}
	
	
	
	/*questo metodo serve ad ottenere , per un utente , tutte le richieste di adozione da lui effettuate*/
	@GetMapping("/user/requests")
	private String  getRequests(Model model){
		UserDetails userDetails=this.globalController.getUser();
		model.addAttribute("userDetails",userDetails);
		Credentials credentials=this.credService.getCredentials(userDetails.getUsername());
		Adopter adopter=credentials.getUser().getAdopter();
		model.addAttribute("adopter",adopter);
		model.addAttribute("requests",adopter.getRequests());
		return "/user/requests.html";
	}
	
	/*questo metodo ha utilità nel solo contesto della consultazione da parte dell'utente : 
	 * consente di visonare la singola richiesta di adozione*/
	@GetMapping("/user/request/{id}")
	private String getRequest(Model model,@PathVariable("id")Long id) {
		model.addAttribute("userDetails", this.globalController.getUser());
		AdoptionRequest request=this.requestService.findById(id);
		model.addAttribute("request",request);
		model.addAttribute("animal",request.getAnimal());
		model.addAttribute("adopter",request.getAdopter());
		return "/user/request.html";
	}
	
	
	@GetMapping("/user/accept/{id}")
	private String acceptAdoption(Model model,@PathVariable("id")Long id) {
		model.addAttribute("userDetails",this.globalController.getUser());
		AdoptionRequest request=this.requestService.findById(id);
		Animal animal = request.getAnimal();
		request.getAdopter().getRequests().remove(request);
		this.requestService.deleteById(id);
		this.adopterService.save(request.getAdopter());
		this.animalService.deleteById(animal.getId());
		return "redirect:/user/requests";
	}
	
	
	@GetMapping("/formNewRequest/{animalId}")
	private String getFormNewRequest(@PathVariable("animalId")Long animalId,Model model) {
		UserDetails userDetails=this.globalController.getUser();
		model.addAttribute("userDetails",userDetails);
		Credentials credentials=this.credService.getCredentials(userDetails.getUsername());
		Adopter adopter=credentials.getUser().getAdopter();
		Animal animal=this.animalService.findById(animalId);
		model.addAttribute("animal",animal);
		model.addAttribute("adopter",adopter);
		model.addAttribute("request",new AdoptionRequest());
		return "/user/formNewRequest.html";
	 }
	
	  @PostMapping("/request/{animalId}/{adopterId}")
	    public String newRequest(
	    						@PathVariable("adopterId")Long adopterId,
	    						@PathVariable("animalId")Long animalId,
	                            @Valid @ModelAttribute("request")AdoptionRequest request,
	                            BindingResult requestBindingResult,
	                            @RequestParam("image") MultipartFile imageFile,  // Add this line to get the image file
	                            Model model) throws IOException {
	        
		  
		   	
		  	Animal animal=this.animalService.findById(animalId);
		  	Adopter adopter=this.adopterService.findById(adopterId);
		  	
		    request.setAnimal(animal);
		    request.setAdopter(adopter);
		    
	        this.requestValidator.validate(request,requestBindingResult);

	        if (!this.requestService.existsAcceptedRequestByAnimalId(animalId)) {
	     
	            // Save the image file to the specified directory
	            if (!imageFile.isEmpty()) {
	            	
	            	// Save the image in the specified directory
	                Files.createDirectories(Paths.get(uploadPath));
	                String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
	                Path imagePath = Paths.get(uploadPath, filename);
	                imageFile.transferTo(imagePath.toFile());

	                // Set the URL to the image in the animal entity
	                request.setUrlImage("/images/" + filename); // URL mapped to the dynamic endpoint
	            }
	            
	            request.setEmissionDate(new Date());
	            request.setAccepted(false); //inizializza l'attributo
	            
	            request.setAnimal(animal);
	            request.setAdopter(adopter);
	            adopter.getRequests().add(request);
	            
	    		this.adopterService.save(adopter);

	            return "redirect:/user/requests";
	        } else {
	            // If there are validation errors, return to the form
	            return "redirect:/formNewRequest/"+animal.getId();  // Replace with your actual form view name
	        }
	    }
	  
	  
	  /*metodo che consente all'utente di eliminare una richiesta effettuata che pero non è
	   * ancora stata accettata*/
	  @GetMapping("/user/deleteRequest/{id}")
	  private String deleteRequest(@PathVariable("id")Long id,Model model) {
		  model.addAttribute("userDetails",this.globalController.getUser());
		  AdoptionRequest request=this.requestService.findById(id);
		  Adopter adopter=request.getAdopter();
		  adopter.getRequests().remove(request);  
		  this.requestService.deleteById(id);
		  this.adopterService.save(adopter);
		  return "redirect:/user/requests";
	  }
	
	
	
	
	
}
