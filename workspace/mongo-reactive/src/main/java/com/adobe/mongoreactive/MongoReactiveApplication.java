package com.adobe.mongoreactive;

import com.adobe.mongoreactive.data.Movie;
import com.adobe.mongoreactive.repo.MovieRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MongoReactiveApplication /*implements  CommandLineRunner*/{
    @Autowired
    MovieRepository movieRepository;

    @Value("classpath:movies.json")
    private Resource resource;

//    @Value("data")
//    int number;

    public static void main(String[] args) {
        SpringApplication.run(MongoReactiveApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        // run()
        return  args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Movie> movieList = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Movie>>() {

                    });
                    System.out.println(movieList);
            Flux.fromIterable(movieList)
                    .delayElements(Duration.ofSeconds(2))
                    .flatMap(movie -> this.movieRepository.save(movie))
                    .doOnComplete(() -> System.out.println("Complete adding movies!!!"))
                    .subscribe();
        };
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//    }
}
