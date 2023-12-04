package com.example.spotify.controller;

import com.example.spotify.HelloApplication;
import com.example.spotify.module.Conexion;
import com.example.spotify.module.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase LoginController
 *
 * Clase que controla login-view
 */
public class LoginController implements Initializable {
    @FXML
    public TextField emailTxt;
    @FXML
    public PasswordField passwTxt;
    @FXML
    public Button loginBtn;
    @FXML
    public Button signUpBtn;
    UserDB userDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Conexion.initConnecion();
        userDB = new UserDB();
    }

    // Comrpueba los datos introducidos y accede al home
    public void login(ActionEvent actionEvent) throws IOException {
        if (userDB.login(emailTxt.getText(),passwTxt.getText())) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Reproductor de música");
            stage.setScene(scene);
            stage.showAndWait();

            // Cierra la pestaña de inicio de sesion al darle al boton de login
            Stage login = (Stage) loginBtn.getScene().getWindow();
            login.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("El correo o la contraseña son erroneos.");
            alert.showAndWait();
        }
    }

    // Abre la view para registrarse
    public void singUp(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signUp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registro de usuario");
        stage.setScene(scene);
        stage.showAndWait();

        Stage signUp = (Stage) signUpBtn.getScene().getWindow();
        signUp.close();
    }


}
