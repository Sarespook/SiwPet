package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;

@Entity
public class AdoptionConditions {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	@Min(1)
	private int mqs;
	
	private Boolean garden;
	
	private String details;

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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public int hashCode() {
		return Objects.hash(details, garden, mqs);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdoptionConditions other = (AdoptionConditions) obj;
		return Objects.equals(details, other.details) && Objects.equals(garden, other.garden)
				&& Objects.equals(mqs, other.mqs);
	}

	
	
		
	
	
	
}
