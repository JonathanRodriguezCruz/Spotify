package com.example.spotify.module;

import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase PlaylistDB
 *
 * Clase que realiza los metodos con las sentecnias sql
 */
public class PlaylistDB {
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;
    static Connection conexion;
    private List<Playlist> playlists;
    private ListView songs;

    // Contructor
    public PlaylistDB() {
        conexion = Conexion.getConnetion();
        playlists = new ArrayList<>();
        songs = new ListView<>();
    }

    // Crea una playlist
    public boolean createPlaylist(String userName,String name) {
        if (conexion != null) {
            String sql = "insert into personalList(userName,playListName) values(?,?)";
            try {
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setString(1,userName);
                preparedStatement.setString(2,name);
                preparedStatement.execute();
            } catch (SQLException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    // Elimina una playlist
    public void deletePlaylist(String name,String userName) {
        if (conexion != null) {
            String sql = "delete from personalList where userName = ? and playListName = ?"; // Consulta SQL con PreparedStatement para evitar la inyección de SQL
            try {
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setString(1,userName);
                preparedStatement.setString(2,name);
                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Saca de la playlist la cancion seleccionada
    public void removeSong() {
        int selectID = songs.getSelectionModel().getSelectedIndex();
        songs.getItems().remove(selectID);
    }

    // Cambia el nombre de la playlist
    public void updatePlaylistName(String newName, String oldName) {
        String sql = "UPDATE personalList SET name = ? WHERE name = ?" ;

        try {
            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString( 1, newName);
            preparedStatement.setString( 2, oldName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Muestra las playlist existentes
    public List<Playlist> showPlaylist(UserDB userDB) {
        String sql = "SELECT * FROM personalList where userName = ?";
        try {
            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, userDB.showUserName());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String userName= resultSet.getString("userName");
                String playListName = resultSet.getString("playListName");

                Playlist p1 = new Playlist(userName, playListName);
                playlists.add(p1);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
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
        return playlists;
    }
}
