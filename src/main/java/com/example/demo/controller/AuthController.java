package com.example.demo.controller;

import com.example.demo.model.LoginRequest;
import com.example.demo.service.UserService;
import com.example.demo.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        log.info("Tentativa de login para usuário '{}'", request.getUsername());

        try {
        var credentials = userService.getCredentials(request.getUsername(), request.getPassword());

        log.info("Login bem-sucedido para usuário '{}'", request.getUsername());
        return ResponseEntity.ok(jwtService.generateKey(credentials));


        } catch(Exception e) {
            log.warn("Falha de login para usuário '{}'", request.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

}
