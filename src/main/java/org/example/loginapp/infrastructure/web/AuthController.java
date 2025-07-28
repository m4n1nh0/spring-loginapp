package org.example.loginapp.infrastructure.web;

import org.example.loginapp.application.command.RegisterUserCommand;
import org.example.loginapp.application.service.AuthService;
import org.example.loginapp.infrastructure.web.dto.RegisterRequestDTO;
import org.example.loginapp.shared.exception.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "success", required = false) String success,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha inválidos.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Você foi desconectado.");
        }
        if (success != null) {
            model.addAttribute("success", "Registro bem-sucedido! Faça login.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequestDTO", new RegisterRequestDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerRequestDTO") RegisterRequestDTO registerRequestDTO,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            RegisterUserCommand command = new RegisterUserCommand(registerRequestDTO.getUsername(),
                    registerRequestDTO.getPassword());
            authService.registerUser(command);
            return "redirect:/login?success";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}