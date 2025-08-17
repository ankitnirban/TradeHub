package com.example.tradehub.auth.service;

import com.example.tradehub.auth.model.LoginRequest;
import com.example.tradehub.auth.model.LoginResponse;
import com.example.tradehub.auth.model.UserCreateRequest;
import com.example.tradehub.user.model.User;
import com.example.tradehub.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest, passwordEncoder);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        User existingUser = userService.findByEmail(loginRequest.getUsername());
        String token = jwtService.generateToken(existingUser);
        String jwtExpirationTime = jwtService.getJwtExpirationTime().toString();
        return new LoginResponse(token, jwtExpirationTime);
    }
}
