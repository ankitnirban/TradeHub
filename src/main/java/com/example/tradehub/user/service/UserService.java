package com.example.tradehub.user.service;

import com.example.tradehub.auth.dto.request.UserCreateRequestDto;
import com.example.tradehub.user.model.User;
import org.springframework.context.annotation.Lazy;

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
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserCreateRequestDto userCreateRequestDto) {
        User newUser = toUserEntity(userCreateRequestDto);
        return userRepository.save(newUser);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with requested email not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with requested email not found"));
        return user;
    }

    private User toUserEntity(UserCreateRequestDto userCreateRequestDto) {
        return new User(
                null,
                userCreateRequestDto.getFirstName(),
                userCreateRequestDto.getLastName(),
                userCreateRequestDto.getEmail(),
                this.passwordEncoder.encode(userCreateRequestDto.getPassword()),
                userCreateRequestDto.getAddress(),
                userCreateRequestDto.getRole()
        );
    }

    public User getUserById(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found.")); //TODO: Create custom exception.
        return existingUser;
    }
}
