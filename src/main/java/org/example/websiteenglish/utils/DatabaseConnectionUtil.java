package org.example.websiteenglish.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/webEnglishDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "89612345";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found", e);
        }
    }

    // Каждый вызов возвращает новое соединение
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
    }
}
