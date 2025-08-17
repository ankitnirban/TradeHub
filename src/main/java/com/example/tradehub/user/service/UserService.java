package com.example.tradehub.user.service;

import com.example.tradehub.auth.model.UserCreateRequest;
import com.example.tradehub.user.model.User;
import com.example.tradehub.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserCreateRequest userCreateRequest) {
        User savedUser = userRepository.save(userCreateRequest.toUserEntity());
        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(username)).orElseThrow(() -> new UsernameNotFoundException("User with requested username not found"));
    }
}
