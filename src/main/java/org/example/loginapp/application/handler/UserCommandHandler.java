package org.example.loginapp.application.handler;

import org.example.loginapp.application.command.RegisterUserCommand;
import org.example.loginapp.domain.model.User;
import org.example.loginapp.domain.service.UserDomainService;
import org.springframework.stereotype.Service;

@Service
public class UserCommandHandler {

    private final UserDomainService userDomainService;

    public UserCommandHandler(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    public User handle(RegisterUserCommand command) {
        return userDomainService.registerNewUser(command.username(), command.password());
    }
}