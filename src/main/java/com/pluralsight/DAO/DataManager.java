package com.pluralsight.DAO;

import com.pluralsight.config.DatabaseConfig;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataManager {
    private final BasicDataSource dataSource;
    private Connection sharedConnection;

    public DataManager(){
        this.dataSource = DatabaseConfig.getDataSource();
    }

    public Connection getConnection() throws SQLException {
        if (sharedConnection == null || sharedConnection.isClosed()){
            sharedConnection = dataSource.getConnection();
        }
        return sharedConnection;
    }

    public void closeConnection(){
        if (sharedConnection != null){
            try {
                sharedConnection.close();
                System.out.println("Database connection is now closed...");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
