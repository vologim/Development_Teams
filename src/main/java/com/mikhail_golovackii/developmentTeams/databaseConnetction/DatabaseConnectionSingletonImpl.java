
package com.mikhail_golovackii.developmentTeams.databaseConnetction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionSingletonImpl {
    private static DatabaseConnectionSingletonImpl instance;
    private Connection connection;
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String dataBaseURL = "jdbc:mysql://localhost/Development_Teams";
    private String user = "bestuser";
    private String password = "Bestuser_111";
    
    private DatabaseConnectionSingletonImpl() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(dataBaseURL, user, password);
        }
        catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
        
    public static DatabaseConnectionSingletonImpl getInstance(){
        if (instance == null){
            instance = new DatabaseConnectionSingletonImpl();
        }
        
        try {
            if (instance.getConnection().isClosed()){
                instance = new DatabaseConnectionSingletonImpl();
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }

    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public void setJDBC_DRIVER(String JDBC_DRIVER) {
        this.JDBC_DRIVER = JDBC_DRIVER;
    }

    public String getURL() {
        return dataBaseURL;
    }

    public void setURL(String dataBaseURL) {
        this.dataBaseURL = dataBaseURL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
