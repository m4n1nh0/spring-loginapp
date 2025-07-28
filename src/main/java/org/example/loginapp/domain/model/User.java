package org.example.loginapp.domain.model;

import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.stream.Collectors;

public class User {

    private UserId id;
    private String username;
    private Password password;
    private Set<String> roles;

    public User(UserId id, String username, Password password, Set<String> roles) {
        Objects.requireNonNull(id, "User ID cannot be null");
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        Objects.requireNonNull(roles, "Roles cannot be null");
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>(roles);
    }

    public static User registerNew(String username, Password password) {
        return new User(UserId.generate(), username, password, Set.of("ROLE_USER"));
    }

    public void changePassword(Password newPassword) {
        Objects.requireNonNull(newPassword, "New password cannot be null");
        this.password = newPassword;
    }

    public void addRole(String role) {
        Objects.requireNonNull(role, "Role cannot be null");
        this.roles.add(role);
    }

    public void removeRole(String role) {
        Objects.requireNonNull(role, "Role cannot be null");
        this.roles.remove(role);
    }

    public UserId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return new HashSet<>(roles);
    }

    public String getRolesAsString() {
        return String.join(",", roles);
    }

    public static User from(UserId id, String username, String hashedPassword, String rolesString) {
        Password password = Password.fromHashed(hashedPassword);
        Set<String> roles = Arrays.stream(rolesString.split(","))
                .collect(Collectors.toSet());
        return new User(id, username, password, roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}