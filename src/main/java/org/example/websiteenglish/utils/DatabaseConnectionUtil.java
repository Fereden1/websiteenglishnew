package org.example.websiteenglish.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DatabaseConnectionUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/webEnglishDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "89612345";
    private static final List<Connection> OPEN_CONNECTIONS = new CopyOnWriteArrayList<>();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found", e);
        }
    }

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            OPEN_CONNECTIONS.add(connection);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
    }

    public static void closeAllConnections() {
        for (Connection connection : OPEN_CONNECTIONS) {
            if (connection != null) {
                try {
                    if (!connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException ignored) {
                }
            }
        }
        OPEN_CONNECTIONS.clear();
    }
}
