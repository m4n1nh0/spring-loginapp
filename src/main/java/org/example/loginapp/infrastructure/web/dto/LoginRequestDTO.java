package org.example.loginapp.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Usuário é obrigatório")
    private String username;
    @NotBlank(message = "Senha é obrigatória")
    private String password;
}
