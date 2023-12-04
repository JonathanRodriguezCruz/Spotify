package com.example.spotify.module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase UserDB
 *
 * Clase que realiza los metodos con las sentecnias sql
 */
public class UserDB {
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;
    static Connection conexion;
    public List<String> usersList;

    // Contructor
    public UserDB() {
        conexion = Conexion.getConnetion();
        usersList = new ArrayList<>();
    }

    // Comprueba si el usuario esta en la DB
    public boolean login(String email, String passw) {
        boolean validLogin = false;
        String sql = "select * from Usuario where email = ? and password = ?"; // Consulta SQL con PreparedStatement para evitar la inyección de SQL

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, passw);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Verificar si se encontraron resultados
                if (resultSet.next()) {
                    validLogin = true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return validLogin;
    }

    // Regsitra al usuario en la DB
    public boolean signUp(String name, String email, String passw) {
        if (conexion != null){
            String sql = "insert into Usuario(userName,email,password) values(?,?,?)";
            try {
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,email);
                preparedStatement.setString(3,passw);
                preparedStatement.execute();
            } catch (SQLException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    // Seleciona el nombre del usuario
    public String showUserName() {
        String name="";
        String sql = "SELECT * FROM Usuario";

        try {
            preparedStatement = conexion.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String userName = resultSet.getString("userName");
                User user = new User(userName);
                name = user.getName();
                usersList.add(user.toString());
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
        return name;
    }
}