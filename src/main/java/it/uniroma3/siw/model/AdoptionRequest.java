package it.uniroma3.siw.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "adoption_request", uniqueConstraints = @UniqueConstraint(columnNames = {"adopter_id", "animal_id"}))
public class AdoptionRequest {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	
	@Temporal(TemporalType.DATE)
	private Date emissionDate;
	
//	@Min(1) 
	private int mqs;

	private boolean garden;
	
	private String urlImage;
	
	private Boolean accepted;

	
	@ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;
	
	@ManyToOne
    @JoinColumn(name = "adopter_id", nullable = false)
    private Adopter adopter;

	
	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public Long getId() {
		return Id;
	}

	public void setGarden(boolean garden) {
		this.garden = garden;
	}
	

	public Adopter getAdopter(){
		return adopter;
	}

	public void setAdopter(Adopter adopter) {
		this.adopter = adopter;
	}

	public Date getEmissionDate() {
		return emissionDate;
	}

	public void setEmissionDate(Date emissionDate) {
		this.emissionDate = emissionDate;
	}

	public int getMqs() {
		return mqs;
	}

	public void setMqs(int mqs) {
		this.mqs = mqs;
	}

	public Boolean getGarden() {
		return garden;
	}

	public void setGarden(Boolean garden) {
		this.garden = garden;
	}
	
	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public Animal getAnimal() {
			return animal;
		}
	
		public void setAnimal(Animal animal) {
			this.animal = animal;
		}
	@Override
	public int hashCode() {
		return Objects.hash(accepted, emissionDate, garden, mqs, urlImage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdoptionRequest other = (AdoptionRequest) obj;
		return accepted == other.accepted && Objects.equals(emissionDate, other.emissionDate)
				&& Objects.equals(garden, other.garden) && mqs == other.mqs && Objects.equals(urlImage, other.urlImage);
	}

	

	

	
	
	
	

}
