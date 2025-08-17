package com.example.tradehub.config;

import com.example.tradehub.user.service.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // Allow H2 console
                        .requestMatchers("/auth/**").permitAll() // Allow all auth endpoints without method restriction
                        .anyRequest().authenticated() // Secure all other requests
                )
                .userDetailsService(userDetailsService) // Configure UserDetailsService
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())); // Allow frames for H2 console
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}