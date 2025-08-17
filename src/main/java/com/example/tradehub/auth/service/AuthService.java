package com.example.tradehub.auth.service;

import com.example.tradehub.auth.model.UserCreateRequest;
import com.example.tradehub.user.model.User;
import com.example.tradehub.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User register(UserCreateRequest userCreateRequest) {
        User registeredUser = userService.createUser(userCreateRequest);
        return registeredUser;
    }

    public User login() {
        return null;
    }
}
