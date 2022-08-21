package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Sidor", "Sidorov", (byte) 25);
        System.out.println(userService.getAllUsers());

        userService.removeUserById(1L);
        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        System.out.println(userService.getAllUsers());

        userService.dropUsersTable();
    }
}
