package com.example.tradehub.auth.controller;

import com.example.tradehub.auth.dto.request.LoginRequestDto;
import com.example.tradehub.auth.dto.request.UserCreateRequestDto;
import com.example.tradehub.auth.dto.response.LoginResponseDto;
import com.example.tradehub.auth.service.AuthService;
import com.example.tradehub.user.dto.response.UserResponseDto;
import com.example.tradehub.user.model.User;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        User registeredUser = authService.register(userCreateRequestDto);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/users/{id}")
            .buildAndExpand(registeredUser.getId())
            .toUri();

        UserResponseDto userResponseDto = UserResponseDto.fromEntity(registeredUser);
        return ResponseEntity.created(location).body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }
}
