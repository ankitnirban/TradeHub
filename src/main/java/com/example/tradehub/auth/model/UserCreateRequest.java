package com.example.tradehub.auth.model;

import com.example.tradehub.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    private String email;

    @Size(min = 8)
    private String password;

    @NotBlank
    private String address;

    @NotNull
    private Role role;
}
