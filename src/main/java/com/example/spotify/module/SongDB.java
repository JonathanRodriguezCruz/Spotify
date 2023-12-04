package com.example.spotify.module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase SongDB
 *
 * Clase que realiza los metodos con las sentecnias sql
 */
public class SongDB {
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;
    static Connection conexion;
    private List<Song> songList;

    // Contructor
    public SongDB() {
        conexion = Conexion.getConnetion();
        songList = new ArrayList<>();
    }

    // Muestra las canciones existentes
    public List<Song> showSongs() {
        String sql = "SELECT * FROM cancion";
        try {
            preparedStatement = conexion.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title= resultSet.getString("title");
                String artist = resultSet.getString("artist");
                double duracion = resultSet.getDouble("duracion");
                String genero = resultSet.getString("genero");

                Song cancion = new Song(title, artist, duracion, genero);
                songList.add(cancion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return songList;
    }
}
