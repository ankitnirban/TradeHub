package com.example.tradehub.auth.service;

import com.example.tradehub.auth.dto.request.LoginRequestDto;
import com.example.tradehub.auth.dto.request.UserCreateRequestDto;
import com.example.tradehub.auth.dto.response.LoginResponseDto;
import com.example.tradehub.user.model.User;
import com.example.tradehub.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthService(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public User register(UserCreateRequestDto userCreateRequestDto) {
        return userService.createUser(userCreateRequestDto);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        User existingUser = userService.findByEmail(loginRequestDto.getUsername());
        String token = jwtService.generateToken(existingUser);
        String jwtExpirationTime = jwtService.getJwtExpirationTime().toString();
        return new LoginResponseDto(token, jwtExpirationTime);
    }
}
