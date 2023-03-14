package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args)   {
        User user1 = new User("Mikhail","Altunin", (byte) 38);
        User user2 = new User("Alexandr","Voloshin", (byte) 37);
        User user3 = new User("Maxim","Melehin", (byte) 32);
        User user4 = new User("Kristina","Altunina", (byte) 38);

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        List<User> list = userService.getAllUsers();
        for (User user:list) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
