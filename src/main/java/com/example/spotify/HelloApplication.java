package com.example.spotify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase HelloApplication
 *
 * Clase ejecutable del programa
 *
 * @author Lucas Sanchez, Felix Montesdeoca, Jonathan Rodriguez
 * @since 3/12/2023
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inicio de sesión");
        Image image = new Image("D:\\2 DAM\\AED\\Spotify\\src\\main\\resources\\img\\icon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}