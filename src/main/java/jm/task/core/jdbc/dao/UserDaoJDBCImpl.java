package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    Connection connection = getConnection();

    public void createUsersTable() {

        String sql = "CREATE TABLE "
                + TABLE_NAME
                + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age INT);";

        try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException ignored) {
            // throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        String sql = "DROP TABLE " + TABLE_NAME;

        try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            //    throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO " + TABLE_NAME + " (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.execute();
            System.out.println("User c именем " + name + " " + lastName + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql= "DELETE FROM " + TABLE_NAME + " WHERE ID = " + id +";";
        try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<User> list = new ArrayList<>();

       try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
           ResultSet resultSet = st.executeQuery(sql);

           while (resultSet.next()) {
               User user = new User();

               user.setId((long) resultSet.getInt(1));
               user.setName(resultSet.getString(2));
               user.setLastName(resultSet.getString(3));
               user.setAge((byte) resultSet.getInt(4));

               list.add(user);
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

        return list;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE " + TABLE_NAME;

        try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
               throw new RuntimeException(e);
        }
    }
}
