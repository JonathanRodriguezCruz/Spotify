package com.example.spotify.controller;

import com.example.spotify.HelloApplication;
import com.example.spotify.module.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.sql.*;
import java.util.*;

/**
 * Clase HomeController
 *
 * Clase que controla home-view
 */
public class HomeController implements Initializable {
    @FXML
    public Label homeTxt;
    @FXML
    public Label playlistTxt;
    @FXML
    public Label songsTxt;
    @FXML
    public Label createPlaylistTxt;
    @FXML
    public ImageView iconImg;
    @FXML
    public Pane homePane;
    @FXML
    public Pane playlistPane;
    @FXML
    public ListView playlistList;
    @FXML
    public Pane songsPane;
    @FXML
    public ListView songsList;
    @FXML
    public Pane createPlaylistPane;
    @FXML
    public ListView createSongsList;
    @FXML
    public ListView addSongsList;
    @FXML
    public Button createPlaylistBtn;
    @FXML
    public ImageView playMusic;
    @FXML
    public ImageView pauseMusic;
    @FXML
    public ProgressBar musicProgress;
    @FXML
    public Slider soundSlider;
    @FXML
    public TextField playlistNameTxt;
    @FXML
    public Button createBtn;
    @FXML
    public Button goInsideBtn;
    UserDB userDB;
    PlaylistDB playlistDB;
    SongDB songDB;
    Connection conexion;
    private static MediaPlayer mediaPlayer;
    private static Media media;
    private static Duration durationPause;
    private Timer timer;
    private TimerTask task;
    private boolean running;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Conexion.initConnecion();
        userDB = new UserDB();
        playlistDB = new PlaylistDB();
        songDB = new SongDB();

        initPane();
        changeVolume();
    }

    // Vuelve visible el panel principal
    public void goPrincipal(MouseEvent mouseEvent) {
        homePane.setVisible(true);
        playlistPane.setVisible(false);
        songsPane.setVisible(false);
        createPlaylistPane.setVisible(false);
    }

    // Vuelve visible el panel para mostrar las playlist
    public void goPlaylist(MouseEvent mouseEvent) {
        homePane.setVisible(false);
        playlistPane.setVisible(true);
        songsPane.setVisible(false);
        createPlaylistPane.setVisible(false);

        loadPlaylists();
    }

    // Vuelve visible el panel mostrar las canciones
    public void goSongs(MouseEvent mouseEvent) {
        homePane.setVisible(false);
        playlistPane.setVisible(false);
        songsPane.setVisible(true);
        createPlaylistPane.setVisible(false);

        loadSongs();
    }

    // Vuelve visible el panel para crear una playlist
    public void goCreatePlaylist(MouseEvent mouseEvent) {
        homePane.setVisible(false);
        playlistPane.setVisible(false);
        songsPane.setVisible(false);
        createPlaylistPane.setVisible(true);

        selectSongs();
    }

    // inicia los panales de la forma adecuada
    private void initPane() {
        homePane.setVisible(true);
        playlistPane.setVisible(false);
        songsPane.setVisible(false);
        createPlaylistPane.setVisible(false);
    }

    // Abre una nueva view para que puedas terminar de crear tu playlist
    public void createPlaylist(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("playlistName-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Crear Playlist");
        stage.setScene(scene);
        stage.showAndWait();
    }

    // Crea una nueva playlist
    public void create(ActionEvent actionEvent) {
        if (!playlistNameTxt.getText().isEmpty()) {
            playlistDB.createPlaylist(userDB.showUserName(),playlistNameTxt.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setContentText("Se creo la Playlist correctamente");
            alert.showAndWait();

            Stage create = (Stage) createBtn.getScene().getWindow();
            create.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Para crear la Playlist necesitas ponerle un nombre");
            alert.showAndWait();
        }
    }

    // Accede a una de las playlist de la view home
    public void openPlaylist(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("playlist-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Playlist");
        stage.setScene(scene);
        stage.showAndWait();
    }

    // Accede a una playlist de la lista de playlist
    public void goInsidePlaylist(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("playlist-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Playlist");
        stage.setScene(scene);
        stage.showAndWait();
    }

    // Muestra las playlist existentes
    public void loadPlaylists() {
        playlistList.getItems().clear();
        for (Playlist playlits : playlistDB.showPlaylist(userDB)) {
            playlistList.getItems().add(playlits);
        }
        if (!playlistDB.showPlaylist(userDB).isEmpty()) {
            playlistList.getSelectionModel().selectFirst();
        }
    }

    // Muestra las canciones existentes
    public void loadSongs() {
        songsList.getItems().clear();
        for (Song songs : songDB.showSongs()) {
            songsList.getItems().add(songs);
        }
        if (!songDB.showSongs().isEmpty()) {
            songsList.getSelectionModel().selectFirst();
        }
    }

    // Elimina la playlist
    public void deletePlaylist(MouseEvent mouseEvent) {
        playlistDB.deletePlaylist(userDB.showUserName(),playlistTxt.getText());
    }

    // Añade las canciones a la lista de canciones seleccionadas depues de darle al icono
    public void addPlaylist(MouseEvent mouseEvent) {
        addSong();
    }

    // Carga las canciones que seran selecionadas par ala futura playlist
    private void selectSongs() {
        createSongsList.getItems().clear();
        for (Song cancion : songDB.showSongs()) {
            createSongsList.getItems().add(cancion);
        }
    }

    // Añade las canciones a la lista de canciones seleccionadas
    public void addSong() {
        Song selectedItems = (Song) createSongsList.getSelectionModel().getSelectedItem();
        System.out.println(selectedItems);
        addSongsList.getItems().addAll(selectedItems);
    }

    // Reproduce la cancion
    public void play(ActionEvent actionEvent) {
        beginTimer();
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            // Si está pausado, reanuda desde la posición almacenada
            mediaPlayer.seek(durationPause);
            mediaPlayer.play();
            System.out.println("Reproducción reanudada desde el segundo: " + durationPause.toSeconds());
        } else {
            // Lógica para manejar el caso cuando mediaPlayer es null o no está en estado pausado
            String nombreCancion = getNameSong();
            if (!nombreCancion.isEmpty()) {
                playDataBases(nombreCancion);
                mediaPlayer.play();
                System.out.println("Reproducción iniciada.");
            } else {
                // Manejar la situación en la que no se selecciona ninguna canción
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Primero elige una canción");
                alert.showAndWait();
            }
        }
    }

    // Para la cancion y pone desde el principio
    public void stop(ActionEvent actionEvent) {
        musicProgress.setProgress(0);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            System.out.println(mediaPlayer.getStatus());
            System.out.println("Reproducción detenida.");
        } else {
            System.out.println("No hay ninguna reproducción en curso para detener.");
        }
    }

    // Pausa la cancion
    public void pause(ActionEvent actionEvent) {
        cancelTimer();
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            // Si está reproduciendo, pausa y almacena la posición actual
            durationPause = mediaPlayer.getCurrentTime();
            mediaPlayer.pause();
            System.out.println("Reproducción en pausa en el segundo: " + durationPause.toSeconds());
        } else {
            System.out.println("No hay ninguna reproducción en curso para pausar.");
        }
    }

    // Cambia el volumen de la cancion
    public void changeVolume() {
        soundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(soundSlider.getValue() * 0.01);
            }
        });
    }

    //
    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                musicProgress.setProgress(current / end);

                if (current / end == 1) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,1000,1000);
    }

    //
    public void cancelTimer(){
        running = false;
        timer.cancel();
    }

    // Pide el nombre de la cancion
    public String getNameSong(){
        Song selectedItems = (Song) songsList.getSelectionModel().getSelectedItem();
        return selectedItems.getTitle();
    }

    // Apartir del objeto media crea un servicio para manejar la reproduccion
    private static void Reproductor(String cancion){
        // Crear el objeto Media
        Media media = new Media(new File(cancion).toURI().toString());
        // Crear el objeto MediaPlayer
        mediaPlayer = new MediaPlayer(media);

        // Crear un Service para manejar la reproducción en un hilo separado
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        mediaPlayer.play();
                        return null;
                    }
                };
            }
        };
        mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("Reproducción finalizada.");
            mediaPlayer.dispose(); // Cerrar el reproductor después de la reproducción
        });

        // Iniciar el servicio
        service.start();
    }

    // Inserta la cancion en la DB
    private void addToDatabase() {
        if (conexion != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos MP3", "*.mp3"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                try {
                    // Leer el archivo de audio como bytes
                    byte[] audioBytes = Files.readAllBytes(selectedFile.toPath());

                    // Insertar información sobre la canción en la base de datos
                    String insertQuery = "INSERT INTO canciones (titulo, artist, audio) VALUES (?, ?, ?)";
                    try (PreparedStatement preparedStatement = conexion.prepareStatement(insertQuery)) {
                        preparedStatement.setString(1, "Título de la canción");
                        preparedStatement.setString(2, "Artista");
                        preparedStatement.setBytes(3, audioBytes);
                        preparedStatement.executeUpdate();
                        System.out.println("Canción agregada a la base de datos");
                    }
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Selecciona la cancion de la DB
    private void playDataBases(String song){
        try {
            PreparedStatement stm = conexion.prepareStatement("SELECT sonido FROM Cancion WHERE title = ?");
            stm.setString(1, song);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                Blob mp3Blob = resultSet.getBlob("sonido");

                // Crear un archivo temporal
                File tempFile = File.createTempFile("tempFile", ".mp3");

                // Escribir el Blob en el archivo temporal
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                InputStream inputStream = mp3Blob.getBinaryStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("Archivo MP3 descargado correctamente en: " + tempFile.getAbsolutePath());
                Reproductor(tempFile.getAbsolutePath());
            } else {
                System.out.println("No se encontró el archivo con el ID especificado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}