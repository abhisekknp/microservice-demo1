package com.demo.microservices.movie.dtos;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;


public class MovieTest {

	@Test
	public void testGetMovies() throws JsonProcessingException {
		List<Movie> moviesList = Lists.newArrayList(new Movie("3 Idiots", "Amir Khan", "Kareena Kapoor", LocalDate.of(2007, 12, 5)),
				new Movie("A wednesday", "Naseeruddin Shah", "N/A", LocalDate.of(2005, 11, 21)),
				new Movie("Sholay", "Amitabh Bacchhan", "Hema Malini", LocalDate.of(1975, 10, 4)),
				new Movie("Baby", "Akchay Kumar", "Tapsi Pannu", LocalDate.of(2015, 05, 25)),
				new Movie("Kesri", "Akchay Kumar", "Pariniti Chopra", LocalDate.of(2018, 9, 10)));
		String json = new ObjectMapper().writeValueAsString(moviesList);
		System.out.println(json);
	}
}
