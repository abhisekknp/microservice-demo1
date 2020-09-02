/**
 * 
 */
package com.demo.microservices.movie.dtos;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents basic movie details.
 *
 * @author Abhishek.Omar
 */
public class Movie extends MovieKey {

	private String leadActor;

	private String leadActress;

	private LocalDate releaseDate;

	/**
	 * Instantiates a new Movie dto.
	 */
	public Movie(final String name, final String leadActor, final String leadActress, final LocalDate releaseDate) {
		super(UUID.randomUUID(),name);
		this.leadActor = leadActor;
		this.leadActress = leadActress;
		this.releaseDate = releaseDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((leadActor == null) ? 0 : leadActor.hashCode());
		result = prime * result + ((leadActress == null) ? 0 : leadActress.hashCode());
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (leadActor == null) {
			if (other.leadActor != null)
				return false;
		} else if (!leadActor.equals(other.leadActor))
			return false;
		if (leadActress == null) {
			if (other.leadActress != null)
				return false;
		} else if (!leadActress.equals(other.leadActress))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [leadActor=" + leadActor + ", leadActress=" + leadActress + ", releaseDate=" + releaseDate
				+ ", getId()=" + getId() + ", getName()=" + getName() + "]";
	}
	
}