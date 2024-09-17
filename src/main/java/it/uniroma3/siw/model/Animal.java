package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Animal {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	@NotBlank
	private String name;

	@NotBlank
	private String species;
	
	private String description;
	
	private String urlImage;
	
	@OneToOne(cascade= {CascadeType.ALL},fetch= FetchType.EAGER)
	private AdoptionConditions conditions;
	
	public Long getId() {
		return Id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getSpecies() {
		return this.species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public AdoptionConditions getConditions() {
		return this.conditions;
	}
	
	public void setConditions(AdoptionConditions conditions) {
		this.conditions=conditions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name, species, urlImage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Objects.equals(species, other.species) && Objects.equals(urlImage, other.urlImage);
	}
	
	
	
}
