package it.uniroma3.siw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.AdoptionConditions;
import it.uniroma3.siw.model.Animal;
import it.uniroma3.siw.service.AnimalService;
import it.uniroma3.siw.validation.AdoptionConditionsValidator;
import it.uniroma3.siw.validation.AnimalValidator;
import jakarta.validation.Valid;

@Controller
public class AnimalController {
	
	
	@Value("${upload.path}")
    private String uploadPath;
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private AnimalService animalService;

	@Autowired
	private AnimalValidator animalValidator;
	
	@Autowired
	private  AdoptionConditionsValidator adoptionConditionsValidator;
	
	@GetMapping("/animals")
	public String getAllAnimals(Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("animals", this.animalService.findAll());
		return "animals.html";
	}
	
	@GetMapping("/user/animals")
	public String getAllUserAnimals(Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("animals", this.animalService.findAll());
		return "/user/animals.html";
	}
	
	@GetMapping("/admin/animals")
	public String getAllAnimalsToCheck(Model model) {
			model.addAttribute("userDetails", this.globalController.getUser());
			model.addAttribute("animals", this.animalService.findAll());
			return "/admin/animals.html";
		}
	
	
	
	@GetMapping("/animal/{id}")
	public String getAnimal(Model model,@PathVariable("id")Long id) {
		model.addAttribute("userDetails", this.globalController.getUser());
		Animal animal=this.animalService.findById(id);
		model.addAttribute("animal", animal);
		model.addAttribute("conditions",animal.getConditions());
		return "animal.html";
	}
	
	@GetMapping("/user/animal/{id}")
	public String getUserAnimal(Model model,@PathVariable("id")Long id) {
		model.addAttribute("userDetails", this.globalController.getUser());
		Animal animal=this.animalService.findById(id);
		model.addAttribute("animal", animal);
		model.addAttribute("conditions",animal.getConditions());
		return "/user/animal.html";
	}
	
	@GetMapping("/formNewAnimal")
	public String showFormNewAnimal(Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("animal",new Animal());
		model.addAttribute("conditions",new AdoptionConditions());
		return "/admin/formNewAnimal.html";
	}
	
	

    @PostMapping("/animal")
    public String newAnimal(@Valid @ModelAttribute("animal") Animal animal,
    						BindingResult animalBindingResult,
                            @Valid @ModelAttribute("conditions") AdoptionConditions conditions,
                            BindingResult conditionsBindingResult,
                            @RequestParam("image") MultipartFile imageFile,  // Add this line to get the image file
                            Model model) throws IOException {
        
        this.animalValidator.validate(animal, animalBindingResult);
        this.adoptionConditionsValidator.validate(conditions, conditionsBindingResult);

        if (!animalBindingResult.hasErrors() && !conditionsBindingResult.hasErrors()) {
            // Save the image file to the specified directory
            if (!imageFile.isEmpty()) {
            	
            	// Save the image in the specified directory
                Files.createDirectories(Paths.get(uploadPath));
                String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path imagePath = Paths.get(uploadPath, filename);
                imageFile.transferTo(imagePath.toFile());

                // Set the URL to the image in the animal entity
                animal.setUrlImage("/images/" + filename); // URL mapped to the dynamic endpoint
            }

            // Link the conditions with the animal
            animal.setConditions(conditions);

            // Save the animal entity
            this.animalService.save(animal);

            return "redirect:/animals";
        }
        return "redirect:/formNewAnimal";
    }
    
    
   

}
