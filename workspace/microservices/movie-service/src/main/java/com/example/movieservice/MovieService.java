package com.example.movieservice;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {
    private final List<Movie> movies = new ArrayList<>();

    public MovieService() {
        movies.add(new Movie("1", "The Shawshank Redemption", "Drama"));
        movies.add(new Movie("2", "The Godfather", "Crime"));
        movies.add(new Movie("3", "The Dark Knight", "Action"));
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Optional<Movie> getMovieById(String id) {
        return movies.stream().filter(movie -> movie.getId().equals(id)).findFirst();
    }

    public Movie addMovie(Movie movie) {
        movie.setId(UUID.randomUUID().toString());
        movies.add(movie);
        return movie;
    }

    public Optional<Movie> updateMovie(String id, Movie updatedMovie) {
        return getMovieById(id).map(movie -> {
            movie.setTitle(updatedMovie.getTitle());
            movie.setGenre(updatedMovie.getGenre());
            return movie;
        });
    }

    public boolean deleteMovie(String id) {
        return movies.removeIf(movie -> movie.getId().equals(id));
    }
}