package com.example.spotify.controller;

import com.example.spotify.module.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase HomeController
 *
 * Clase que controla playlist-view
 */
public class PlaylistController implements Initializable {
    @FXML
    public Label playlistNameLabel;
    @FXML
    public Label userNameLabel;
    @FXML
    public ImageView playlistImg;
    @FXML
    public ImageView deleteImg;
    @FXML
    public ImageView addImg;
    @FXML
    public ImageView updateImg;
    @FXML
    public ListView playlistSongsList;
    @FXML
    public TextField newNameTxt;
    @FXML
    public Button saveNameBtn;
    @FXML
    public Pane changeNamePane;
    UserDB userDB;
    PlaylistDB playlistDB;
    SongDB songDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Conexion.initConnecion();
        userDB = new UserDB();
        playlistDB = new PlaylistDB();
        songDB = new SongDB();

        playlistNameLabel.setText(Playlist.class.getName());
        userNameLabel.setText(userDB.showUserName());

        changeNamePane.setVisible(false);
    }

    // Elimina la playlist
    public void delete(MouseEvent mouseEvent) {
        playlistDB.removeSong();
    }

    // Abre el panle para cambiar el nombre a la playlist
    public void update(MouseEvent mouseEvent) {
        changeNamePane.setVisible(true);
    }

    // Confirma el cambio de nombre
    public void saveName(ActionEvent actionEvent) {
        if (!newNameTxt.getText().isEmpty()) {
            playlistDB.updatePlaylistName(newNameTxt.getText(),userNameLabel.getText());
            changeNamePane.setVisible(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Para cambiar el nombre de la Playlist necesitas escribir al menos un caracter");
        }
    }
}
