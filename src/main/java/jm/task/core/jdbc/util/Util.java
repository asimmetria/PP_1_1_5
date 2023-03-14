package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class
Util {
    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "CoHh9MKA";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql";


    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
