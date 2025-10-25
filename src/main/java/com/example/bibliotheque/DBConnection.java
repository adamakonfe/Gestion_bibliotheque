package com.example.bibliotheque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/bibliotheque";
    private static final String USER = "root"; // À adapter selon ton installation
    private static final String PASSWORD = ""; // À adapter aussi

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
