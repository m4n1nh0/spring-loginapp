package org.example.loginapp.domain.repository;

import org.example.loginapp.domain.model.User;
import org.example.loginapp.domain.model.UserId;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId id);
    Optional<User> findByUsername(String username);
    User save(User user);
    void delete(User user);
}