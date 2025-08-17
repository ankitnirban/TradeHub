package com.example.tradehub.user.service;

import com.example.tradehub.auth.model.UserCreateRequest;
import com.example.tradehub.user.model.User;
import com.example.tradehub.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserCreateRequest userCreateRequest, PasswordEncoder passwordEncoder) {
        User savedUser = userRepository.save(toUserEntity(userCreateRequest, passwordEncoder));
        return savedUser;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with requested email not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with requested username not found"));
    }

    private User toUserEntity(UserCreateRequest userCreateRequest, PasswordEncoder passwordEncoder) {
        return new User(
                null,
                userCreateRequest.getFirstName(),
                userCreateRequest.getLastName(),
                userCreateRequest.getEmail(),
                passwordEncoder.encode(userCreateRequest.getPassword()),
                userCreateRequest.getAddress(),
                userCreateRequest.getRole()
        );
    }
}
