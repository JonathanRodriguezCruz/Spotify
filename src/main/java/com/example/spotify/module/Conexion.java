package com.example.spotify.module;

import java.sql.*;

/**
 * Clase Conexion
 *
 * Clase que conecta el programa con la DB
 */
public class Conexion {
    private static Connection connection;
    private static String url = "";
    private static String user = "root";
    private static String password = "";

    // Iniciamos la conexion
    public static Connection initConnecion() {
        try {
            connection = DriverManager.getConnection(url,user,password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Comprueba si la conexion esta iniciada
    public static Connection getConnetion() {
        if (connection != null) {
            return connection;
        }
        return null;
    }

    // Cierra la conexion
    public static boolean closeConnection() {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
