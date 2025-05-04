package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private static final Pattern SANITIZE_PATTERN = Pattern.compile("[^a-zA-Z0-9@._-]");

    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = sanitize(username);
        this.password = sanitize(password);
        validate();
    }

    private void validate() {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Usuário invalido");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Senha invalida");
        }
    }

    private String sanitize(String input) {
        if (input == null) return null;
        return SANITIZE_PATTERN.matcher(input).replaceAll("");
    }
}
