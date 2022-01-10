package com.mikhail_golovackii.developmentTeams.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionSingleton {

    private static DBConnectionSingleton instance;
    private Connection connection;
    private FileInputStream fis;
    private Properties property;
    private final String path = "src/main/resources/application.properties";
    
    private DBConnectionSingleton() {
        try {
            property = new Properties();
            fis = new FileInputStream(path);
            property.load(fis);

            Class.forName(property.getProperty("driver"));
            connection = DriverManager.getConnection(property.getProperty("url"), 
                                                     property.getProperty("username"), 
                                                     property.getProperty("password"));
            
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("File: application-database.properties not found");
        }
    }

    public static DBConnectionSingleton getInstance() {
        if (instance == null) {
            instance = new DBConnectionSingleton();
        }

        try {
            if (instance.getConnection().isClosed()) {
                instance = new DBConnectionSingleton();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return instance;
    }

    public static PreparedStatement preparedStatement(String query) {
        try {
            return getInstance().getConnection().prepareCall(query);
        } catch (SQLException ex) {
            throw new RuntimeException("SQLException");
        }
    }
    
    public static PreparedStatement preparedStatement(String query, int RSType, int RSConcurrency) {
        try {
            return getInstance().getConnection().prepareCall(query, RSType, RSType);
        } catch (SQLException ex) {
            throw new RuntimeException("SQLException");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
