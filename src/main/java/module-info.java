module com.example.spotify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;


    opens com.example.spotify.controller to javafx.fxml;
    exports com.example.spotify.controller;
    opens com.example.spotify to javafx.fxml;
    exports com.example.spotify;
}