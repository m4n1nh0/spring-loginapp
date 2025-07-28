package org.example.loginapp.domain.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

public class Password {

    private final String hashedPassword; // Armazena a senha já hashada

    private Password(String hashedPassword) {
        Objects.requireNonNull(hashedPassword, "Hashed password cannot be null");
        this.hashedPassword = hashedPassword;
    }

    public static Password encode(String rawPassword, BCryptPasswordEncoder passwordEncoder) {
        Objects.requireNonNull(rawPassword, "Raw password cannot be null");
        String encodedPassword = passwordEncoder.encode(rawPassword);
        return new Password(encodedPassword);
    }

    public static Password fromHashed(String hashedPassword) {
        Objects.requireNonNull(hashedPassword, "Hashed password cannot be null");
        return new Password(hashedPassword);
    }

    public boolean matches(String rawPassword, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.hashedPassword);
    }

    public String getHashedValue() {
        return hashedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(hashedPassword, password.hashedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashedPassword);
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}