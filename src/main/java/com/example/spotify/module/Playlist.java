package com.example.spotify.module;

/**
 * Clase User
 *
 * Contiene toda la informacion de cada playlist
 */
public class Playlist {
    private int idList;
    private String name;
    private String userName;
    private int numSongs;
    private double duration;

    // Contructor

    public Playlist(String name) {
        this.name = name;
        this.userName = "";
        this.numSongs = 0;
        this.duration = 0;
    }

    public Playlist(String name, String userName) {
        this.name = name;
        this.userName = userName;
        this.numSongs = 0;
        this.duration = 0;
    }

    // Getters & Setters
    public int getIdList() {
        return idList;
    }
    public void setIdList(int idList) {
        this.idList = idList;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getNumSongs() {
        return numSongs;
    }
    public void setNumSongs(int numSongs) {
        this.numSongs = numSongs;
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Nombre = " + name + ", nombre del creador= " + userName;
    }
}
