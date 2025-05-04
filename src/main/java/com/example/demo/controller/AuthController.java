package com.example.demo.controller;

import com.example.demo.model.LoginRequest;
import com.example.demo.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        log.info("Tentativa de login para usuário '{}'", request.getUsername());

        boolean valid = authService.validateCredentials(request.getUsername(), request.getPassword());

        if (valid) {
            log.info("Login bem-sucedido para usuário '{}'", request.getUsername());
            return ResponseEntity.ok("Login successful");

        } else {
            log.warn("Falha de login para usuário '{}'", request.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

}
