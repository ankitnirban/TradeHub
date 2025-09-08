package com.example.tradehub.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank
    private String username;
    @Size(min = 8)
    private String password;
}
