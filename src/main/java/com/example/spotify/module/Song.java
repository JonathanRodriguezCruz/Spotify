package com.example.spotify.module;

/**
 * Clase Song
 *
 * Contiene toda la informacion de cada cancion
 */
public class Song {
    private String title;
    private String artist;
    private double duration;
    private String genre;

    // Constructor
    public Song(String title, String artist, double duration, String genre) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.genre = genre;
    }

    // Getters & Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return title + ", " + artist + ", " + duration + ", " + genre;
    }
}
