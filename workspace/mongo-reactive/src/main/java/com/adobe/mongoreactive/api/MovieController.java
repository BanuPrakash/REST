package com.adobe.mongoreactive.api;

import com.adobe.mongoreactive.data.Movie;
import com.adobe.mongoreactive.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping(value = "/movie", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Movie> getMovies() {
        return this.movieRepository.findBy(); // tailable
    }

    @PostMapping(value = "/movie")
    public Mono<String> addMovie(@RequestBody Movie m) {
        this.movieRepository.save(m).subscribe();
       return  Mono.just("Movie added !!!");
    }
}
