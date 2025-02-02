package com.cinema.entities;

// represents a movie entity
public class Movies {
    private int movieId;
    private String title;
    private String genre;
    private int duration;
    private String rating;

    public Movies() {}

    public Movies(int movieId, String title, String genre, int duration, String rating) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
    }

    // getters and setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    // returns the movie title as a string representation
    @Override
    public String toString() {
        return title;
    }
}
