package com.example.spotify.module;

/**
 * Clase User
 *
 * Contiene toda la informacion de cada user
 */
public class User {
    private String name;
    private String email;
    private String password;

    // Cosntructor
    public User() {
        this.name = "";
        this.email = "";
        this.password = "";
    }
    public User(String userName) {
        this.name = userName;
        this.email = "";
        this.password = "";
    }
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Nombre = " + name + ", email = " + email + ", contraseña = " + password;
    }
}
