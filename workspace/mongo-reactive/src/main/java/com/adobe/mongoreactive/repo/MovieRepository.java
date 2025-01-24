package com.adobe.mongoreactive.repo;

import com.adobe.mongoreactive.data.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
    @Tailable
    Flux<Movie> findBy();
}
