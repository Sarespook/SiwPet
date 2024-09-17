package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Adopter {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String surname;
	
	@OneToMany(cascade= {CascadeType.ALL},orphanRemoval=true,fetch=FetchType.LAZY)
	private List<AdoptionRequest> requests;

	public Long getId() {

		return this.Id;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}

	

	public List<AdoptionRequest> getRequests() {
		return requests;
	}


	public void setRequests(List<AdoptionRequest> requests) {
		this.requests = requests;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, surname);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adopter other = (Adopter) obj;
		return Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
	}


	

	
}
