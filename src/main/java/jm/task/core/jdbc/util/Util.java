package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class
Util {
    public static final String USER_NAME = "admin";
    public static final String PASSWORD = "CoHh9MKA";
    public static final String URL = "jdbc:mysql://localhost:3306/mysql";
    public static final String TABLE_NAME ="abc";


    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //    static {
//        try {
//            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//            System.out.println("Connection is OK");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            throw new RuntimeException();
//        }
//    }
//
//    static {
//        try {
//            statement = connection.createStatement();
//            System.out.println("Statement is OK");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            throw new RuntimeException();
//        }
//    }
//



}
