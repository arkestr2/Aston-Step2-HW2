package com.example.usercrud.service;

import com.example.usercrud.dao.Dao;
import com.example.usercrud.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final Dao<User> dao;

    public UserService(Dao<User> dao) {
        this.dao = dao;
    }

    public void save(User user) {
        log.info("Creating user: {}", user);
        dao.save(user);
    }

    public Optional<User> findById(Long id) {
        log.info("Searching user by ID: {}", id);

        Optional<User> user = dao.findById(id);

        if (user.isEmpty()) {
            log.warn("User not found: {}", id);
        }

        return user;
    }

    public List<User> findAll() {
        log.info("Searching all users");
        return dao.findAll();
    }

    public Optional<User> update(Long id, User userUpdates) {
        log.info("Updating user with ID: {}", id);

        Optional<User> userToUpdate = findById(id);

        if (userToUpdate.isEmpty()) {
            return Optional.empty();
        }

        String name = userUpdates.getName();
        String email = userUpdates.getEmail();
        int age = userUpdates.getAge();

        if (name != null)
            userToUpdate.get().setName(name);
        if (email != null)
            userToUpdate.get().setEmail(email);
        if (age != 0)
            userToUpdate.get().setAge(age);

        User updatedUser = dao.update(userToUpdate.get());
        log.info("Updated user with ID: {}", id);

        return Optional.ofNullable(updatedUser);
    }

    public Optional<User> delete(Long id) {
        log.info("Deleting user with ID: {}", id);

        Optional<User> userToDelete = findById(id);

        if (userToDelete.isEmpty()) {
            return Optional.empty();
        }

        dao.delete(userToDelete.get());
        log.info("Deleted user with ID: {}", id);

        return userToDelete;
    }
}
