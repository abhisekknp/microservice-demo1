/**
 * 
 */
package com.demo.microservices.movie.dtos;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents basic movie details.
 *
 * @author Abhishek.Omar
 */
public class Movie extends MovieKey {

	private String leadActor;

	private String leadActress;

	private LocalDate releaseDate;
	
	public Movie() {
		super();
	}

	/**
	 * Instantiates a new Movie dto.
	 */
	public Movie(final String name, final String leadActor, final String leadActress, final LocalDate releaseDate) {
		super(UUID.randomUUID(),name);
		this.leadActor = leadActor;
		this.leadActress = leadActress;
		this.releaseDate = releaseDate;
	}

	public String getLeadActor() {
		return leadActor;
	}

	public String getLeadActress() {
		return leadActress;
	}
	@JsonSerialize (using = CustomDateToStringSerializer.class)
	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setLeadActor(String leadActor) {
		this.leadActor = leadActor;
	}

	public void setLeadActress(String leadActress) {
		this.leadActress = leadActress;
	}

	public void setReleaseDate(LocalDate releaseDate) {
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
	
	/**
	 * Custom date serializer that converts the date to string before sending it out
	 * 
	 * @author Abhishek.Omar
	 *
	 */
	static class CustomDateToStringSerializer extends JsonSerializer<LocalDate> {

		   @Override 
		   public void serialize(LocalDate value, 
		      JsonGenerator generator, SerializerProvider arg2) throws IOException { 
		      generator.writeString(value.toString()); 
		   } 
	}
	
}