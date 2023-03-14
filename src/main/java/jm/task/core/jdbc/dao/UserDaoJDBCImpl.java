package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

//-------------------------------------------------------------------------------
    private void myRollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void myExecuteUpdate(String sql){
        try (Connection connection = getConnection()) {
            try (Statement st = connection.createStatement()) {
                st.executeUpdate(sql);
            } catch (SQLException e) {
                myRollback(connection);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void myExecuteUpdateIgnoredSQLEx(String sql) {
        try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException ignored) {

        }

    }
//-------------------------------------------------------------------------------

    public UserDaoJDBCImpl() {

    }

    //  Connection connection = getConnection();

    public void createUsersTable() {
        myExecuteUpdateIgnoredSQLEx("CREATE TABLE "
                + TABLE_NAME
                + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age TINYINT);");
    }

    public void dropUsersTable() {
        myExecuteUpdateIgnoredSQLEx("DROP TABLE " + TABLE_NAME);
    }


    /*  Если я правильно понял:
        "в дао слое требуется реализовать логику транзакций на методах, которые изменяют бд" -
        это значит , что нужно отключать автокоммит перед попыткой запросОВ , коммитить вручную после запросОВ и
        в кэтче делать роллБэк , если при исполнению запросов выскочило исключение.

        Отключение автокоммита и коммите врунчнею не делал , потому что запрос в каждом методе ОДИН и значит нет смысла
        выключать/включать коммит , поскольку эти действия реализованы по умолчанию в JDBC

     */

    public void saveUser(String name, String lastName, byte age) {               // with rollBack
        String sql = "INSERT INTO " + TABLE_NAME + " (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);
                ps.executeUpdate();
                System.out.println("User c именем " + name + " " + lastName + " добавлен в базу данных");
            } catch (SQLException e) {
                myRollback(connection);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {        // with rollBack
        myExecuteUpdate("DELETE FROM " + TABLE_NAME + " WHERE ID = " + id + ";");
    }

    public void cleanUsersTable() {             // with rollBack
        myExecuteUpdate("TRUNCATE TABLE " + TABLE_NAME);
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
}
