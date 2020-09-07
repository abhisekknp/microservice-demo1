package com.demo.microservices.review.apis;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.demo.microservices.review.dtos.Movie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * The {@link HystrixCommand} works since Spring makes a proxy to intercept the
 * request and call the fallback method if the method errs.<br>
 * <br>
 * 
 * Hence the {@link HystrixCommand} needs to be in separate {@link Component} or
 * {@link Service} stereotypes so that Spring can proxy them.
 * 
 * @author Abhishek.Omar
 *
 */
@Service
public class MovieService {

	private static final Logger LOGGER = Logger.getLogger(MovieService.class.getName());

	@Autowired
	private OAuth2RestTemplate restTemplate;

	/**
	 * Returns the comments for the task; note that this applies a circuit
	 * breaker that would return the default value if the comments-webservice
	 * was down.
	 * 
	 * Discussion on HystrixProperty
	 * <ol>
	 * <li><b>execution.isolation.strategy:</b> The value of "SEMAPHORE" enables
	 * Hystrix to use the current thread for executing this command. Since we
	 * need to use the parent HttpRequest to pass the OAuth2 access token, we
	 * are constrained in using the calling thread. The number of concurrent
	 * requests that can be made to the command is limited by the semaphore(or
	 * counter) value; default is 10.In a normal scenario, you would use
	 * isolation strategy of "THREAD" that executes the Hystrix command in a
	 * separate thread pool. This is desirable because it doesn't block the
	 * calling thread and lets us handle faulty client libraries, client
	 * performance considerations etc.</li>
	 * <li><b>circuitBreaker.requestVolumeThreshold:</b>This property sets the
	 * minimum number of requests in a rolling window that will trip the
	 * circuit. For example, if the value is 20, then if only 19 requests are
	 * received in the rolling window (say a window of 10 seconds) the circuit
	 * will not trip open even if all 19 failed. We have a lower value in this
	 * sample application so as to trip early.</li>
	 * <li><b>circuitBreaker.sleepWindowInMilliseconds:</b>This property sets
	 * the amount of time, after tripping the circuit, to reject requests before
	 * allowing attempts again to determine if the circuit should again be
	 * closed. We again have a lower value so that Hystrix tries to pass single
	 * request to the called service quickly.</li>
	 * </ol>
	 * 
	 * @param taskId
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "getFallbackMovieForReviews", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000") })
	public Movie getMovieForReview(String movieName) {
		/**
		 * We can't use the context root for comments webservice because of
		 * problems getting Spring cloud contract to work with it.
		 * 
		 * If we add context-root, then we cannot mock the server on the
		 * comments-webservice side and we will have to make calls with
		 * <code>testMode = 'EXPLICIT'</code> for the producer tests on the
		 * comments-webservice side. This doesn't work on the
		 * comments-webservice side since there is no mock OAuth2 token that
		 * gets injected there.
		 * 
		 * We can't introduce an annotation on the generated test class since
		 * there is NO original test and hence the @WithMockOAuth2Token
		 * mechanism that we use in the task-webservice project can't be used in
		 * the comments-webservice project.
		 */
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info(String.format("Checking movie for movie name [%s]", movieName));
		}
		Movie movie =  restTemplate.getForObject(String.format("http://movie-service/%s", movieName),
				Movie.class);
		System.out.println(movie);
		return movie;
	}

	/**
	 * Gets the default comments for task. Need to add the suppress warning
	 * since the method is not directly used by the class.
	 *
	 * @return the default comments for task
	 */
	@SuppressWarnings("unused")
	private Movie getFallbackMovieForReviews(String movieName) {
		System.out.println("fetching default movie for movie name :"+movieName);
		// Get the default movie
		Movie movie = new Movie();
		movie.setId(UUID.randomUUID());
		movie.setName(movieName);
		return movie;
	}
}
