package com.demo.microservices.review.dtos;

/**
 * Represents Review.
 *
 * @author Abhishek.Omar
 */
public class Review extends ReviewKey {
	
	private Integer rating;
	
	private String reviewString;

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getReviewString() {
		return reviewString;
	}

	public void setReviewString(String reviewString) {
		this.reviewString = reviewString;
	}

}
