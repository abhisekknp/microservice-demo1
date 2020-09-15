package com.demo.microservices.movie.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.microservices.movie.dtos.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
@RequestMapping("")
public class MovieController {

	/**
	 * Get movie for specific name that is passed in the path.
	 * 
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/movies/{name}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Movie getMovieByName(@PathVariable("name") String name) throws IOException {
		
		for (Movie movie : getMovieList()) {
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
	 * @throws IOException 
	 */
	@RequestMapping(value = "/movies", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Movie> getAllMovies() throws IOException {
		return getMovieList();
	}
	
	private List<Movie> getMovieList() throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("/app/movies.json");
		if (null == is) {
			is = this.getClass().getClassLoader().getResourceAsStream("movies.json");
		}
		String json = IOUtils.toString(is, StandardCharsets.UTF_8.name());
		return new ObjectMapper().readValue(json, new TypeReference<List<Movie>>(){});
	}
}
