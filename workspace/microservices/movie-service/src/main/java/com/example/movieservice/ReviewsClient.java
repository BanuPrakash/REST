package com.example.movieservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reviews-service")
public interface ReviewsClient {

    @GetMapping("/reviews/{movieId}")
    List<Review> getReviewsByMovieId(@PathVariable("movieId") String movieId);
}