package com.demo.microservices.movie.rest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.microservices.movie.dtos.Movie;

/**
 * REST endpoint for the movie functionality<br>
 * <br>
 * 
 * Note that this endpoint is supposed to be consumed by the Movie webservice and
 * is not accessible to the general public; i.e. the api-gateway doesn't handle
 * requests for movie-webservice.
 * 
 * @author Abhishek.Omar
 *
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

	private static final Logger LOGGER = Logger.getLogger(MovieController.class.getName());

	private final List<Movie> moviesList;

	public MovieController() {
		this.moviesList = Arrays.asList(new Movie("3 Idiots", "Amir Khan", "Kareena Kapoor", LocalDate.of(2007, 12, 5)),
				new Movie("A wednesday", "Naseeruddin Shah", "N/A", LocalDate.of(2005, 11, 21)),
				new Movie("Sholay", "Amitabh Bacchhan", "Hema Malini", LocalDate.of(1975, 10, 4)),
				new Movie("Baby", "Akchay Kumar", "Tapsi Pannu", LocalDate.of(2015, 05, 25)),
				new Movie("Kesri", "Akchay Kumar", "Pariniti Chopra", LocalDate.of(2018, 9, 10)));
	}

	/**
	 * Get movie for specific name that is passed in the path.
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Movie getMovieByName(@PathVariable("name") String name) {
		
		for (Movie movie : moviesList) {
			if (movie.getName().equalsIgnoreCase(name)) {
				return movie;
			}
		}
		return null;
	}
	
	/**
	 * Get all movies.
	 * 
	 * @return 
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Movie> getAllMovies() {
		return moviesList;
	}
}
