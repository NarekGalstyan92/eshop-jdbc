package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {

    private static DBConnectionProvider dbConnectionProvider = null;

    private Connection connection;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/eshop";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "rootroot";


    private DBConnectionProvider() {
    }

    // lazy
    public static DBConnectionProvider getInstance() {
        if (dbConnectionProvider == null) {
            dbConnectionProvider = new DBConnectionProvider();
        }
        return dbConnectionProvider;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD );
            }
        }catch (SQLException e) {
                e.printStackTrace();
            }

        return connection;
    }
}
