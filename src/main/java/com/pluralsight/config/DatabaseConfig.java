package com.pluralsight.config;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/accountingledger";
    private static final String PROPERTIES_FILE = "application.properties";

    private static BasicDataSource dataSource;

    public static BasicDataSource getDataSource(){
        if (dataSource == null){
            dataSource = new BasicDataSource();

            try {
                Properties props = loadProperties();
                dataSource.setUrl(url);
                dataSource.setUsername(props.getProperty("username"));
                dataSource.setPassword(props.getProperty("password"));
            } catch (IOException e) {
                System.err.println("Error loading database properties: " + e.getMessage());
                System.out.println("check connection between application.properties and the Database Config class");
                System.exit(1);
            }
        }
        return dataSource;
    }

    private static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)){
            props.load(fis);
        }
        return props;
    }

    public static void closeDataSource(){
        if (dataSource != null){
            try {
                dataSource.close();
            } catch (Exception e) {
                System.err.println("Error closing data source: " + e.getMessage());
            }
        }
    }
}
