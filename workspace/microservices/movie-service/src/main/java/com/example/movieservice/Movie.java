package com.example.movieservice;

public class Movie {
    private String id;
    private String title;
    private String genre;
    public Movie() {
    }
    public Movie(String id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}