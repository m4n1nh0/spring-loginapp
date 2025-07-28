package org.example.loginapp.application.service;

import org.example.loginapp.application.command.RegisterUserCommand;
import org.example.loginapp.application.handler.UserCommandHandler;
import org.example.loginapp.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserCommandHandler userCommandHandler;

    public AuthService(UserCommandHandler userCommandHandler) {
        this.userCommandHandler = userCommandHandler;
    }

    public User registerUser(RegisterUserCommand command) {
        return userCommandHandler.handle(command);
    }

}