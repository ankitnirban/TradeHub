package com.example.tradehub.user.service;

import com.example.tradehub.auth.dto.request.UserCreateRequestDto;
import com.example.tradehub.user.dto.response.UserResponseDto;
import com.example.tradehub.user.model.User;

import com.example.tradehub.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toUserResponseDto).toList();
    }

    public UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {
        return toUserResponseDto(userRepository.save(toUserEntity(userCreateRequestDto)));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with requested email not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with requested email not found"));
        return user;
    }

    public UserResponseDto getUserById(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
        return toUserResponseDto(existingUser);
    }

    private UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPassword(),
            user.getRole()
        );
    }

    private User toUserEntity(UserCreateRequestDto userCreateRequestDto) {
        return new User(
            null,
            userCreateRequestDto.getFirstName(),
            userCreateRequestDto.getLastName(),
            userCreateRequestDto.getEmail(),
            userCreateRequestDto.getPassword(),
            userCreateRequestDto.getAddress(),
            userCreateRequestDto.getRole()
        );
    }
}
