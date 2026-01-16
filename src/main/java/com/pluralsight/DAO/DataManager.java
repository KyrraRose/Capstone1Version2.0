package com.pluralsight.DAO;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataManager {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/sakila";
    private static Connection connection = null;
    private static final Scanner keyboard = new Scanner(System.in);

    public DataManager(String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Error when loading connection. Exiting application.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}