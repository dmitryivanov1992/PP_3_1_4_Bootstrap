package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;


public interface UserDAO {
    void addUser(User user);

    void deleteUser(int id);

    void editUser(User user);

    List<User> listUsers();

    User getUserById(int id);

    User findByUsername(String username);
}
