package com.example.tradehub.auth.model;

import com.example.tradehub.user.model.Role;
import com.example.tradehub.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
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

    public User toUserEntity() {
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setAddress(this.address);
        user.setRole(this.role);
        return user;
    }
}
