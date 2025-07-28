package org.example.loginapp.domain.service;

import org.example.loginapp.domain.model.Password;
import org.example.loginapp.domain.model.User;
import org.example.loginapp.domain.repository.UserRepository;
import org.example.loginapp.shared.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDomainService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDomainService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(String username, String rawPassword) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("O usuário '" + username + "' já existe.");
        }

        Password encodedPassword = Password.encode(rawPassword, passwordEncoder);

        User newUser = User.registerNew(username, encodedPassword);

        return userRepository.save(newUser);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}