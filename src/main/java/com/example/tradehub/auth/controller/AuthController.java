package com.example.tradehub.auth.controller;

import com.example.tradehub.auth.model.UserCreateRequest;
import com.example.tradehub.auth.service.AuthService;
import com.example.tradehub.user.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User registeredUser = authService.register(userCreateRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        User user = authService.login();
        return ResponseEntity.ok("Login successful");
    }
}
