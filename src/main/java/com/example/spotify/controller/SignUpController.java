package com.example.spotify.controller;

import com.example.spotify.HelloApplication;
import com.example.spotify.module.Conexion;
import com.example.spotify.module.UserDB;
import javafx.event.ActionEvent;
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
 * Clase SignUpController
 *
 * Clase que controla signUp-view
 */
public class SignUpController implements Initializable {

    public TextField userNameTxt;
    public TextField userEmailTxt;
    public PasswordField passTxt;
    public Button cancelBtn;
    public Button signUpBtn;
    UserDB userDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Conexion.initConnecion();
        userDB = new UserDB();
    }

    // Registra al usuario en la DB
    public void signUp(ActionEvent actionEvent) throws IOException {
        if((!userEmailTxt.getText().contains("@") || !userEmailTxt.getText().endsWith(".com") || passTxt.getText().length()<= 4)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("El correo debe contener @ y .com, la contraseña debe ser de minimo 4 caracteres");
            alert.showAndWait();

        }else { // Una vez se haya registrado correctamente te lleva a la view de inisio de sesion
            if (userDB.signUp(userNameTxt.getText(),userEmailTxt.getText(),passTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Se registro correctamente");
                alert.showAndWait();

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Inicio de sesion");
                stage.setScene(scene);
                stage.showAndWait();

                Stage cancel = (Stage) cancelBtn.getScene().getWindow();
                cancel.close();
            }
        }
    }

    // Vuelve a la view de login
    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inicio de sesion");
        stage.setScene(scene);
        stage.showAndWait();

        Stage cancel = (Stage) cancelBtn.getScene().getWindow();
        cancel.close();
    }
}
