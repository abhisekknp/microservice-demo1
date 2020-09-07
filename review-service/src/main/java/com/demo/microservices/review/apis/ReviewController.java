package com.demo.microservices.review.apis;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.microservices.review.dtos.Movie;
import com.demo.microservices.review.dtos.Review;
import com.demo.microservices.review.dtos.ReviewKey;
import com.google.common.collect.Maps;

/**
 * REST endpoint for the Review functionality
 * 
 * @author Abhishek.Omar
 *
 */
@RestController
@RequestMapping("")
public class ReviewController {

	@Autowired
	private MovieService movieService;

	private Map<ReviewKey, Review> userReviewMap = Maps.newConcurrentMap();

	/**
	 * Get all tasks
	 * 
	 * @return
	 */
	@PostMapping(value = "/postReview", headers = "Accept=application/json")
	public void postReview(Review review, Principal principal) {
		validateReviewRequest(review);
		String movieName = review.getName();
		Movie movie = validateAndGetMovieObject(review.getName());
		String userName = principal.getName();
		ReviewKey reviewKey = new ReviewKey();
		reviewKey.setUserName(userName);
		reviewKey.setName(movieName);
		reviewKey.setId(movie.getId());
		reviewKey = userReviewMap.putIfAbsent(reviewKey, review);
		if(null != reviewKey) {
			System.out.println("user has already reviewed this movie, use update service to update the reviews!!");
		} else {
			System.out.println("review submitted successfully!!");
		}
	}
	
	@GetMapping (value = "{movieName}/getReview",  headers = "Accept=application/json")
	public List<Review> getReviewsForMovie(@PathVariable("movieName") String movieName) {
		Movie movie = validateAndGetMovieObject(movieName);
		List<Review> reviewsList = new ArrayList<>();
		userReviewMap.keySet().forEach(reviewKey -> {
			if(reviewKey.getId() == movie.getId() && reviewKey.getName().equalsIgnoreCase(movie.getName())) {
				reviewsList.add(userReviewMap.get(reviewKey));
			}
		});
		return reviewsList;
	}

	private Movie validateAndGetMovieObject(String name) {
		return movieService.getMovieForReview(name);
	}

	private void validateReviewRequest(Review review) {
		if (null == review || null == review.getName() || null == review.getRating()
				|| null == review.getReviewString()) {
			throw new ValidationException("Invalid Request!!");
		}
	}

}
