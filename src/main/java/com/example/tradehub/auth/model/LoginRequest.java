package com.example.tradehub.auth.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String username;
    @Size(min = 8)
    private String password;
}
